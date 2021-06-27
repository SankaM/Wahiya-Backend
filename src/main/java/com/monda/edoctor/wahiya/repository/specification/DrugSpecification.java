package com.monda.edoctor.wahiya.repository.specification;

import com.monda.edoctor.wahiya.model.DrugEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DrugSpecification {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Specification<DrugEntity> textInAllColumns(Object value) {
        return (root, query, builder) -> builder.or(root.getModel().getDeclaredSingularAttributes().stream()
                .filter(attr -> filter(value, attr))
                .map(attr -> map(value, root, builder, attr))
                .toArray(Predicate[]::new));
    }

    private static boolean filter(Object value, SingularAttribute<?, ?> attr) {
        if (attr.getName().equalsIgnoreCase("imageUrl")) {
            return false;
        } else if (attr.getJavaType().getSimpleName().equalsIgnoreCase("Integer")) {
            try {
                Integer.parseInt(value.toString());
                return true;
            } catch (Exception e) {
                return false;
            }
        } else if (attr.getJavaType().getSimpleName().equalsIgnoreCase("LocalDate")) {
            return isDate(value.toString(), DATE_FORMAT);
        } else {
            return attr.getJavaType().equals(value.getClass());
        }
    }

    private static Object map(Object value, Root<?> root, CriteriaBuilder builder, SingularAttribute<?, ?> a) {
        switch (a.getJavaType().getSimpleName()) {
            case "String":
                return builder.like(builder.lower(root.get(a.getName())), getString((String) value));
            case "Integer":
                return builder.equal(root.get(a.getName()), value);
            case "LocalDate":
                return builder.equal(root.get(a.getName()), convertToLocalDate(value.toString(), DATE_FORMAT));//date mapping
            default:
                return null;
        }
    }

    private static String getString(String text) {
        if (!text.contains("%")) {
            text = "%" + text.toLowerCase() + "%";
        }
        return text;
    }


    private static boolean isDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static LocalDate convertToLocalDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date dateToConvert = format.parse(date);
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (ParseException e) {
        }
        return null;
    }
}