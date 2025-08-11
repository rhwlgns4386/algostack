package com.hunko.algostack.algorithm.util;

import com.tngtech.archunit.thirdparty.com.google.common.base.CaseFormat;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DatabaseCleanup implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<Pair> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(this::toPair)
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (Pair pair : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + pair.tableName).executeUpdate();
            if(pair.hasGeneratedValue){
                entityManager.createNativeQuery("ALTER TABLE " + pair.tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
            }
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private Pair toPair(EntityType<?> entityType) {
        String tableName = toTableName(entityType);
        boolean hasAutoGenerate = hasAutoGenerate(entityType);
        return new Pair(tableName, hasAutoGenerate);
    }

    private boolean hasAutoGenerate(EntityType<?> entityType) {
        return entityType.getJavaType().getAnnotation(GeneratedValue.class) != null;
    }

    private String toTableName(EntityType<?> entityType) {
        Table table = entityType.getJavaType().getAnnotation(Table.class);
        if(table != null) {
            String name = table.name();
            if(name != null && !name.isEmpty()) {
                return name;
            }
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.getName());
    }

    private static class Pair{
        private String tableName;
        private boolean hasGeneratedValue;
        public Pair(String tableName, boolean hasGeneratedValue) {
            this.tableName = tableName;
        }
    }
}
