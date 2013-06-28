/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.users.impl;

import java.util.Collection;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UsersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserImpl#getPermissions <em>Permissions</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserImpl#getEmail <em>Email</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UserImpl extends CDOObjectImpl implements User {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPassword()
     * @generated
     * @ordered
     */
    protected static final String PASSWORD_EDEFAULT = null;

    /**
     * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDisplayName()
     * @generated
     * @ordered
     */
    protected static final String DISPLAY_NAME_EDEFAULT = null;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final String TYPE_EDEFAULT = null;

    /**
     * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEmail()
     * @generated
     * @ordered
     */
    protected static final String EMAIL_EDEFAULT = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UserImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsersPackage.Literals.USER;
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
    @Override
    public String getName() {
        return (String)eDynamicGet(UsersPackage.USER__NAME, UsersPackage.Literals.USER__NAME, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setName(String newName) {
        eDynamicSet(UsersPackage.USER__NAME, UsersPackage.Literals.USER__NAME, newName);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPassword() {
        return (String)eDynamicGet(UsersPackage.USER__PASSWORD, UsersPackage.Literals.USER__PASSWORD, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPassword(String newPassword) {
        eDynamicSet(UsersPackage.USER__PASSWORD, UsersPackage.Literals.USER__PASSWORD, newPassword);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<Role> getRoles() {
        return (EList<Role>)eDynamicGet(UsersPackage.USER__ROLES, UsersPackage.Literals.USER__ROLES, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<Permission> getPermissions() {
        return (EList<Permission>)eDynamicGet(UsersPackage.USER__PERMISSIONS, UsersPackage.Literals.USER__PERMISSIONS, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDisplayName() {
        return (String)eDynamicGet(UsersPackage.USER__DISPLAY_NAME, UsersPackage.Literals.USER__DISPLAY_NAME, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDisplayName(String newDisplayName) {
        eDynamicSet(UsersPackage.USER__DISPLAY_NAME, UsersPackage.Literals.USER__DISPLAY_NAME, newDisplayName);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getType() {
        return (String)eDynamicGet(UsersPackage.USER__TYPE, UsersPackage.Literals.USER__TYPE, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(String newType) {
        eDynamicSet(UsersPackage.USER__TYPE, UsersPackage.Literals.USER__TYPE, newType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getEmail() {
        return (String)eDynamicGet(UsersPackage.USER__EMAIL, UsersPackage.Literals.USER__EMAIL, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEmail(String newEmail) {
        eDynamicSet(UsersPackage.USER__EMAIL, UsersPackage.Literals.USER__EMAIL, newEmail);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    @Override
    public EList<Permission> getAllPermissions() {
        EList<Permission> allPermissions = new BasicEList<Permission>(getPermissions());
        for(Role role : getRoles())
            allPermissions.addAll(role.getAllPermissions());
        return allPermissions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public boolean hasPermission(String requestedPermission) {
        EList<Permission> allPermissions = getAllPermissions();
        for (Permission availablePermission : allPermissions) {
            if(matches(availablePermission,requestedPermission))
                return true;
        }
        return false;
    }

    private boolean matches(Permission availablePermission, String requestedPermission) {
        if(availablePermission.getName()==null || availablePermission.getName().isEmpty())
            return false;
        String permissionRegex = availablePermission.getName().replace("*", ".*");
        permissionRegex += ".*";
        return Pattern.matches(permissionRegex, requestedPermission);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case UsersPackage.USER__NAME:
                return getName();
            case UsersPackage.USER__PASSWORD:
                return getPassword();
            case UsersPackage.USER__ROLES:
                return getRoles();
            case UsersPackage.USER__PERMISSIONS:
                return getPermissions();
            case UsersPackage.USER__DISPLAY_NAME:
                return getDisplayName();
            case UsersPackage.USER__TYPE:
                return getType();
            case UsersPackage.USER__EMAIL:
                return getEmail();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case UsersPackage.USER__NAME:
                setName((String)newValue);
                return;
            case UsersPackage.USER__PASSWORD:
                setPassword((String)newValue);
                return;
            case UsersPackage.USER__ROLES:
                getRoles().clear();
                getRoles().addAll((Collection<? extends Role>)newValue);
                return;
            case UsersPackage.USER__PERMISSIONS:
                getPermissions().clear();
                getPermissions().addAll((Collection<? extends Permission>)newValue);
                return;
            case UsersPackage.USER__DISPLAY_NAME:
                setDisplayName((String)newValue);
                return;
            case UsersPackage.USER__TYPE:
                setType((String)newValue);
                return;
            case UsersPackage.USER__EMAIL:
                setEmail((String)newValue);
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
        switch (featureID) {
            case UsersPackage.USER__NAME:
                setName(NAME_EDEFAULT);
                return;
            case UsersPackage.USER__PASSWORD:
                setPassword(PASSWORD_EDEFAULT);
                return;
            case UsersPackage.USER__ROLES:
                getRoles().clear();
                return;
            case UsersPackage.USER__PERMISSIONS:
                getPermissions().clear();
                return;
            case UsersPackage.USER__DISPLAY_NAME:
                setDisplayName(DISPLAY_NAME_EDEFAULT);
                return;
            case UsersPackage.USER__TYPE:
                setType(TYPE_EDEFAULT);
                return;
            case UsersPackage.USER__EMAIL:
                setEmail(EMAIL_EDEFAULT);
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
        switch (featureID) {
            case UsersPackage.USER__NAME:
                return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
            case UsersPackage.USER__PASSWORD:
                return PASSWORD_EDEFAULT == null ? getPassword() != null : !PASSWORD_EDEFAULT.equals(getPassword());
            case UsersPackage.USER__ROLES:
                return !getRoles().isEmpty();
            case UsersPackage.USER__PERMISSIONS:
                return !getPermissions().isEmpty();
            case UsersPackage.USER__DISPLAY_NAME:
                return DISPLAY_NAME_EDEFAULT == null ? getDisplayName() != null : !DISPLAY_NAME_EDEFAULT.equals(getDisplayName());
            case UsersPackage.USER__TYPE:
                return TYPE_EDEFAULT == null ? getType() != null : !TYPE_EDEFAULT.equals(getType());
            case UsersPackage.USER__EMAIL:
                return EMAIL_EDEFAULT == null ? getEmail() != null : !EMAIL_EDEFAULT.equals(getEmail());
        }
        return super.eIsSet(featureID);
    }

} //UserImpl
