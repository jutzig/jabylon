/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resolvable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ResolvableImpl#getPercentComplete <em>Percent Complete</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ResolvableImpl extends CDOObjectImpl implements Resolvable {
    /**
     * The default value of the '{@link #getPercentComplete() <em>Percent Complete</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPercentComplete()
     * @generated
     * @ordered
     */
    protected static final int PERCENT_COMPLETE_EDEFAULT = 0;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ResolvableImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PropertiesPackage.Literals.RESOLVABLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getPercentComplete() {
        return (Integer)eDynamicGet(PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE, PropertiesPackage.Literals.RESOLVABLE__PERCENT_COMPLETE, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPercentComplete(int newPercentComplete) {
        eDynamicSet(PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE, PropertiesPackage.Literals.RESOLVABLE__PERCENT_COMPLETE, newPercentComplete);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public URI fullPath() {
        EObject parent = eContainer();
        while(parent!=null)
        {
            if (parent instanceof Resolvable) {
                Resolvable resolvable = (Resolvable) parent;
                return resolvable.fullPath().appendSegments(relativePath().segments());
            }
        }
        return URI.createURI("");
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public abstract URI relativePath();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public URI absolutPath() {
        EObject object = this;
        while((object!=null) && !(object instanceof Workspace))
            object = object.eContainer();

        if (object instanceof Workspace) {
            Workspace workspace = (Workspace) object;
            URI root = workspace.getRoot();
            if(root==null)
                return null;
            if(root.isRelative())
            {
                File f = new File("");
                URI workingDir = URI.createFileURI(f.getAbsolutePath());
                root = workingDir.appendSegments(root.segments());
            }
            return root.appendSegments(fullPath().segments());
        }
        return null;

    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public int updatePercentComplete()
    {
        EObject container = eContainer();
        int percentComplete = internalUpdatePercentComplete();
        if(percentComplete!=getPercentComplete())
        {
            setPercentComplete(percentComplete);
            while(container!=null)
            {
                if (container instanceof Resolvable) {
                    Resolvable resolvable = (Resolvable) container;
                    resolvable.updatePercentComplete();
                }
                container = container.eContainer();
            }
        }
        return percentComplete;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Resolvable resolveChild(URI path)
    {
        if(path.segmentCount()==0 || relativePath().equals(path))
            return this;
        EList<EObject> contents = eContents();

        for (EObject eObject : contents)
        {
            if (eObject instanceof Resolvable)
            {
                Resolvable resolvable = (Resolvable)eObject;
                URI relativePath = resolvable.relativePath();
                String childPath = relativePath.path();
                if(childPath.startsWith("/"))
                    childPath = childPath.substring(1); //TODO: get rid of this, necessary for PropertyFileDescriptor currently
                if(path.path().startsWith(childPath))
                {
                    String[] segments = path.segments();
                    String[] remainder = new String[segments.length-relativePath.segmentCount()];
                    System.arraycopy(segments, relativePath.segmentCount(), remainder, 0, remainder.length);
                    URI shorterURI = URI.createHierarchicalURI(remainder, null, null);
                    return resolvable.resolveChild(shorterURI);
                }

            }
        }
        return null;
    }

    public abstract int internalUpdatePercentComplete();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID)
        {
            case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
                return getPercentComplete();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID)
        {
            case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
                setPercentComplete((Integer)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID)
        {
            case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
                setPercentComplete(PERCENT_COMPLETE_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID)
        {
            case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
                return getPercentComplete() != PERCENT_COMPLETE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

} //ResolvableImpl
