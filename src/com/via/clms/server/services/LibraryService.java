package com.via.clms.server.services;

import java.util.Arrays;

import com.via.clms.proxy.ILibraryService;
import com.via.clms.shared.Library;

/**
 * Library service that can be used to manage libraries
 */
public class LibraryService implements ILibraryService, Service {
	
	/** * */
	public int libraryCapacity;
	
	/** * */
	public int currentLibrary;
	
	/** * */
	Library[] libraryList;

	public LibraryService() {
		libraryList = new Library[libraryCapacity];
	}

	@Override
	public boolean createLibrary(byte[] reqToken, String name) {
		if (currentLibrary >= 0 && currentLibrary < libraryCapacity) {
		libraryList[currentLibrary] = new Library(currentLibrary, name);
		currentLibrary++;
		return true;
		
		} 
		if (currentLibrary < 0 
				|| currentLibrary > libraryCapacity) {
			throw new IndexOutOfBoundsException();
		}
		return false;
		}

	@Override
	public Library getLibraryByLID(byte[] reqToken, int lid) {
		for (Library l : libraryList) {
			if (l.lid == lid) {
				return l;
			}
		}
		return null;
	}

	@Override
	public Library[] getLibraries(byte[] reqToken, int offset, int length) {
		return Arrays.copyOfRange(libraryList, offset, offset + length);
	}

	public void setLibraryCapacity(int libraryCapacity) {
		this.libraryCapacity = libraryCapacity;
	}

	public int getLibraryCapacity() {
		return libraryCapacity;
	}

	public void setCurrentLibrary(int currentLibrary) {
		this.currentLibrary = currentLibrary;
	}
	
	public int getCurrentLibrary() {
		return currentLibrary;
	}
	
	@Override
	public boolean onConfigure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub

	}

}
