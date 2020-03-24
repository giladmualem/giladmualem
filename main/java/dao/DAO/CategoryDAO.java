package dao.DAO;

import entites.Category;

public interface CategoryDAO {
    Boolean isExist(Category category);

    void addCategory(Category category);

    Long getIdCategory(Category category);

    void deleteCategory(Long id);
}