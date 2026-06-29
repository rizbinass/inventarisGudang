package com.inventarisgudang.dao;

import com.inventarisgudang.model.Barang;
import java.util.List;

/**
 * Data Access Object for Barang (Product/Item).
 * Handles all database operations related to Barang.
 */
public class BarangDAO {
    
    // TODO: Implement CRUD operations
    
    /**
     * Retrieves all barang from database.
     * 
     * @return list of all Barang objects
     */
    public List<Barang> findAll() {
        // TODO: Implement findAll
        return null;
    }

    /**
     * Retrieves a specific barang by ID.
     * 
     * @param id the barang ID
     * @return Barang object or null if not found
     */
    public Barang findById(int id) {
        // TODO: Implement findById
        return null;
    }

    /**
     * Saves a new barang to database.
     * 
     * @param barang the Barang object to save
     * @return true if successful, false otherwise
     */
    public boolean save(Barang barang) {
        // TODO: Implement save
        return false;
    }

    /**
     * Updates an existing barang in database.
     * 
     * @param barang the Barang object to update
     * @return true if successful, false otherwise
     */
    public boolean update(Barang barang) {
        // TODO: Implement update
        return false;
    }

    /**
     * Deletes a barang from database.
     * 
     * @param id the barang ID to delete
     * @return true if successful, false otherwise
     */
    public boolean delete(int id) {
        // TODO: Implement delete
        return false;
    }

    /**
     * Searches barang by name.
     * 
     * @param nama the name to search for
     * @return list of matching Barang objects
     */
    public List<Barang> searchByNama(String nama) {
        // TODO: Implement searchByNama
        return null;
    }

    /**
     * Retrieves barang by category ID.
     * 
     * @param kategoriId the category ID
     * @return list of Barang objects in the category
     */
    public List<Barang> findByKategori(int kategoriId) {
        // TODO: Implement findByKategori
        return null;
    }
}
