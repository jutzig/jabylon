package de.jutzig.jabylon.updatecenter.repository;

import java.util.List;

import org.apache.felix.bundlerepository.Resource;
import org.osgi.framework.Bundle;

public interface OBRRepositoryService {

    List<Bundle> listInstalledBundles();

    List<Resource> getAvailableResources(ResourceFilter filter);

    Resource[] findResources(String id);

    void install(String resourceId);

    void install(Resource... resource);

}
