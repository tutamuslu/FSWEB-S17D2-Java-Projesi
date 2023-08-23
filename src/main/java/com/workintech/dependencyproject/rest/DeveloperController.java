package com.workintech.dependencyproject.rest;

import com.workintech.dependencyproject.model.Developer;
import com.workintech.dependencyproject.model.DeveloperResponse;
import com.workintech.dependencyproject.model.JuniorDeveloper;
import com.workintech.dependencyproject.model.MidDeveloper;
import com.workintech.dependencyproject.tax.DeveloperTax;
import com.workintech.dependencyproject.tax.Taxable;
import com.workintech.dependencyproject.validation.DeveloperValidations;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private Map<Integer, Developer> developers;
    private Taxable taxable;
    @PostConstruct
    public void init(){
        developers = new HashMap<>();

    }
    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }
    @GetMapping("/")
    public List<Developer>get(){
        return developers.values().stream().toList();

    }
    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable int id){
        String result = DeveloperValidations.checkDeveloperNotAdded(developers, id);
        if(result != null){
            new DeveloperResponse(1, result, null);
        }
        return new DeveloperResponse(0, "", developers.get(id));
    }

    @PutMapping("/")
    public DeveloperResponse save(@RequestBody Developer developer){
        String result = DeveloperValidations.checkDeveloperAdded(developers, developer.getId());
        if(result != null){
            new DeveloperResponse(1, result, null);
        }
        Developer saveDeveloper = createDeveloper(developer);
        developers.put(developer.getId(), developer);
        return new DeveloperResponse(0, result, saveDeveloper);
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable int id, @RequestBody Developer developer){
        String result = DeveloperValidations.checkDeveloperNotAdded(developers, id);
        if(result != null){
            new DeveloperResponse(1, result, null);
        }
        Developer updatedDeveloper = createDeveloper(developer);
        developers.put(id, updatedDeveloper);
        return new DeveloperResponse(0, result, developers.get(id));
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable int id){
        String result = DeveloperValidations.checkDeveloperNotAdded(developers, id);
        if(result != null){
            new DeveloperResponse(1, result, null);
        }
        Developer developer = developers.get(id);
        developers.remove(developer);
        return new DeveloperResponse(0, result, developer);
    }

    private Developer createDeveloper(Developer developer){
        Developer saveDeveloper;
        if(developer.getExperience().name().equalsIgnoreCase("junior")){
            saveDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getSimpleTaxRate(), developer.getExperience());
        }else if(developer.getExperience().name().equalsIgnoreCase("mid")){
            saveDeveloper = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getMiddleTaxRate(), developer.getExperience());
        }else if(developer.getExperience().name().equalsIgnoreCase("senior")){
            saveDeveloper = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - developer.getSalary() * taxable.getUpperTaxRate(), developer.getExperience());
        }else{
            saveDeveloper = null;
        }
        return saveDeveloper;
    }
}
