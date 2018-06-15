package com.cloud.demo.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
public abstract class BaseService<T> implements Service<T> {

    @Autowired
    protected MyMapper<T> myMapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public BaseService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }


    public void save(List<T> models) {
        myMapper.insertList(models);
    }

    public void deleteById(Integer id) {
        myMapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        myMapper.deleteByIds(ids);
    }

    public T findById(Integer id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return myMapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return myMapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return myMapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return myMapper.selectAll();
    }

    @Override
    public List<T> select(T record) {
        return myMapper.select(record);
    }

    @Override
    public T selectByKey(Object key) {
        return myMapper.selectByPrimaryKey(key);
    }

    @Override
    public List<T> selectAll() {
        return myMapper.selectAll();
    }

    @Override
    public T selectOne(T record) {
        return myMapper.selectOne(record);
    }

    @Override
    public int selectCount(T record) {
        return myMapper.selectCount(record);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return myMapper.selectByExample(example);
    }

    @Override
    public int save(T record) {
        return myMapper.insertSelective(record);
    }

    @Override
    public int update(T entity) {
        return myMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(T record) {
        return myMapper.delete(record);
    }

    @Override
    public int deleteByKey(Object key) {
        return myMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int batchDelete(List<T> list) {
        int result = 0;
        for (T record : list) {
            int count = myMapper.delete(record);
            if (count < 1) {
                throw new RuntimeException("插入数据失败!");
            }
            result += count;
        }
        return result;
    }

    @Override
    public int selectCountByExample(Object example) {
        return myMapper.selectCountByExample(example);
    }

    @Override
    public int updateByExample(T record, Object example) {
        return myMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int deleteByExample(Object example) {
        return myMapper.deleteByPrimaryKey(example);
    }

    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return myMapper.selectByRowBounds(record, rowBounds);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return myMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

}
