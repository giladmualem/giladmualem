package dao.DAO;

import entites.Category;
import exceptions.NotExistException;

public interface CategoryDAO {
    Boolean isExist(Category category) throws NotExistException;

    void addCategory(Category category);

    Long getIdCategory(Category category);

    void deleteCategory(Long id);
}