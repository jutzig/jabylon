package de.jutzig.jabylon.rest.ui.model;

import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.properties.Resolvable;


public interface RepositoryLookup
{
    <T extends Resolvable<?, ?>> T lookup(String path);

    <T extends Resolvable<?, ?>> T lookup(List<String> path);

    <T extends Resolvable<?, ?>> T lookup(URI uri);

    <T extends CDOObject> T lookup(CDOID id);
    
    <T extends CDOObject> T lookupWithTransaction(CDOID id);

}



