/**
 * 
 */
package de.jutzig.jabylon.rest.ui.security;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.strategies.CompoundAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonAuthorizationStrategy extends CompoundAuthorizationStrategy {

	public JabylonAuthorizationStrategy(IRoleCheckingStrategy roleCheckingStrategy) {
		add(new AnnotationsRoleAuthorizationStrategy(roleCheckingStrategy)
		{
			@Override
			public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
				boolean permitted = super.isInstantiationAuthorized(componentClass);
				if(!permitted)
					throw new RestartResponseAtInterceptPageException(LoginPage.class);
				return permitted;
			}
		});
		add(new MetaDataRoleAuthorizationStrategy(roleCheckingStrategy)
		{
			@Override
			public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
				boolean permitted = super.isInstantiationAuthorized(componentClass);
				if(!permitted)
					throw new RestartResponseAtInterceptPageException(LoginPage.class);
				return permitted;
			}
		});
	}

	
	
}
