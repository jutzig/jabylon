/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.tests;

import org.junit.Assert;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.ScanConfiguration;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Scan Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.ScanConfiguration#getExcludes() <em>Excludes</em>}</li>
 *   <li>{@link org.jabylon.properties.ScanConfiguration#getIncludes() <em>Includes</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ScanConfigurationTest extends TestCase {

    /**
     * The fixture for this Scan Configuration test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScanConfiguration fixture = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(ScanConfigurationTest.class);
    }

    /**
     * Constructs a new Scan Configuration test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScanConfigurationTest(String name) {
        super(name);
    }

    /**
     * Sets the fixture for this Scan Configuration test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void setFixture(ScanConfiguration fixture) {
        this.fixture = fixture;
    }

    /**
     * Returns the fixture for this Scan Configuration test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScanConfiguration getFixture() {
        return fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#setUp()
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(PropertiesFactory.eINSTANCE.createScanConfiguration());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#tearDown()
     * @generated
     */
    @Override
    protected void tearDown() throws Exception {
        setFixture(null);
    }

    /**
     * Tests the '{@link org.jabylon.properties.ScanConfiguration#getExcludes() <em>Excludes</em>}' feature getter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.ScanConfiguration#getExcludes()
     * @generated
     */
    public void testGetExcludes() {
    }

    public void testGetExcludesWithDefault() {
        assertEquals(0, getFixture().getExcludes().size());
    }

    public void testGetExcludesWithLF() {
        getFixture().setExclude("A\nB\nC\n");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getExcludes().toArray(new String[0]));
    }

    public void testGetExcludesWithCRLF() {
        getFixture().setExclude("A\r\nB\r\nC\r\n");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getExcludes().toArray(new String[0]));
    }

    public void testGetExcludesWithCR() {
        getFixture().setExclude("A\rB\rC\r");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getExcludes().toArray(new String[0]));
    }

    public void testGetExcludesWithSpaces() {
        getFixture().setExclude(" A \nB\nC \n");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getExcludes().toArray(new String[0]));
    }

    /**
     * Tests the '{@link org.jabylon.properties.ScanConfiguration#getIncludes() <em>Includes</em>}' feature getter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.ScanConfiguration#getIncludes()
     * @generated
     */
    public void testGetIncludes() {
    }

    public void testGetIncludesWithDefault() {
        assertEquals(1, getFixture().getIncludes().size());
        Assert.assertArrayEquals(new String[]{"**/*.properties"}, getFixture().getIncludes().toArray(new String[0]));
    }

    public void testGetIncludesWithLF() {
        getFixture().setInclude("A\nB\nC\n");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getIncludes().toArray(new String[0]));
    }

    public void testGetIncludesWithCRLF() {
        getFixture().setInclude("A\r\nB\r\nC\r\n");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getIncludes().toArray(new String[0]));
    }

    public void testGetIncludesWithCR() {
        getFixture().setInclude("A\rB\rC\r");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getIncludes().toArray(new String[0]));
    }

    public void testGetIncludesWithSpaces() {
        getFixture().setInclude(" A \nB\nC \n");
        Assert.assertArrayEquals(new String[]{"A","B","C"}, getFixture().getIncludes().toArray(new String[0]));
    }

} //ScanConfigurationTest
