package de.jutzig.jabylon.updatecenter.repository;

import java.util.List;

import org.apache.felix.bundlerepository.Resource;

public interface OBRRepositoryService {

	public abstract List<Resource> listInstalledBundles();

}