package de.jutzig.jabylon.ui.beans;

public class ProjectBean {
    private String name;
    private String repositoryURI;
    private String branch;
    private String username;
    private String password;
    private boolean checkoutImmediately;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRepositoryURI() {
        return repositoryURI;
    }
    public void setRepositoryURI(String repositoryURI) {
        this.repositoryURI = repositoryURI;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isCheckoutImmediately() {
        return checkoutImmediately;
    }
    public void setCheckoutImmediately(boolean checkoutImmediately) {
        this.checkoutImmediately = checkoutImmediately;
    }



}
