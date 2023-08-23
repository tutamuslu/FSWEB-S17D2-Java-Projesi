package com.workintech.dependencyproject.validation;

import com.workintech.dependencyproject.model.Developer;

import java.util.Map;

public class DeveloperValidations {
    public static String checkId(int id){
        if(id <= 0){
            return "Id alanı 0 ve daha küçük olamaz";
        }
        return  null;
    }

    public static String checkName(String name){
        if(name == null || name.isEmpty()){
            return "Name alanı boş olamaz";
        }
        return null;
    }

    public  static String checkSalary(double salary){
        if(salary <= 0){
            return "Salary alanı 0 veya daha küçük olamaz";
        }
        return null;
    }

    public static String checkDeveloperAdded(Map<Integer, Developer> developers, int id){
        if(developers.containsKey(id)){
            return "Bu developer zaten eklenmiş";
        }
        return null;
    }

    public static String checkDeveloperNotAdded(Map<Integer, Developer> developers, int id){
        if(!developers.containsKey(id)){
            return "Developer sistemde bulunamadı.";
        }
        return null;
    }
}
