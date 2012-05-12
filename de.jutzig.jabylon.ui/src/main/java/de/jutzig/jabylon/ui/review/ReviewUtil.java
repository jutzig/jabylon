/**
 * 
 */
package de.jutzig.jabylon.ui.review;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.review.PropertyFileReview;
import de.jutzig.jabylon.review.ReviewFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class ReviewUtil {

	private ReviewUtil() {
		// hide utility constructor
	}

	public static PropertyFileReview getReviewFor(PropertyFileDescriptor descriptor) {
		Resource resource = getReviewResource(descriptor);
		if (resource != null && !resource.getContents().isEmpty()) {
			EObject object = resource.getContents().get(0);
			if (object instanceof PropertyFileReview) {
				PropertyFileReview review = (PropertyFileReview) object;
				return review;

			}
		}
		return null;
	}

	public static Resource getReviewResource(PropertyFileDescriptor descriptor) {
		return getReviewResource(descriptor,descriptor.cdoView());

	}
	
	public static Resource getReviewResource(PropertyFileDescriptor descriptor, CDOView view) {
		String path = "review/" + getIDString(descriptor);
		if (view.hasResource(path)) {
			return view.getResource(path);
		}
		return null;

	}
	
	
	public static boolean hasReviewResource(PropertyFileDescriptor descriptor) {
		String path = "review/" + getIDString(descriptor);
		return (descriptor.cdoView().hasResource(path));
	}

	/**
	 * Opens a new transaction <strong>but does not close or commit it!</strong>
	 * @param descriptor
	 * @return
	 */
	public static PropertyFileReview getOrCreateReview(PropertyFileDescriptor descriptor) {
		CDOTransaction transaction = descriptor.cdoView().getSession().openTransaction();
		return getOrCreateReview(descriptor,transaction);

	}
	
	

	public static PropertyFileReview getOrCreateReview(PropertyFileDescriptor descriptor, CDOTransaction transaction) {
		CDOResource resource = transaction.getOrCreateResource("review/" + getIDString(descriptor));
		EList<EObject> contents = resource.getContents();
		PropertyFileReview review = null;

		if (contents.isEmpty()) {
			review = ReviewFactory.eINSTANCE.createPropertyFileReview();
			contents.add(review);
		} else {
			review = (PropertyFileReview) contents.get(0);
		}

		return review;

	}
	
	public static String getIDString(CDOObject object)
	{
		StringBuilder builder = new StringBuilder();
		CDOIDUtil.write(builder, object.cdoID());
		return builder.toString();
	}
}
