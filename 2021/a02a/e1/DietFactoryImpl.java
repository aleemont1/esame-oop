package a02a.e1;

import java.util.HashMap;
import java.util.Map;

public class DietFactoryImpl implements DietFactory {


    @Override
    public Diet standard() {
        return new Diet() {

            private final Map<String,Map<Nutrient, Integer>> products = new HashMap<>();
            
            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                if(products.containsKey(name)) {
                    throw new IllegalArgumentException();
                }
                products.put(name, nutritionMap);
            }


            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                final Map<Nutrient, Double> calories = new HashMap<>();
                
            }
            
        };
    }

    @Override
    public Diet lowCarb() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lowCarb'");
    }

    @Override
    public Diet highProtein() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'highProtein'");
    }

    @Override
    public Diet balanced() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'balanced'");
    }
    
}
