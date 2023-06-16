package com.cookandroid.algorithmproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuOutputActivity extends AppCompatActivity {
    TextView tv_menu, tv_delete_menu;
    EditText edt_menu, edt_delete_menu;
    String result, result2;
    int material;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_output);

        Intent intent = getIntent();
        material = intent.getIntExtra("material", 0);

        tv_menu = (TextView) findViewById(R.id.tv_menu);
        tv_delete_menu = (TextView) findViewById(R.id.tv_delete_menu);

        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish("야채김밥", 900 * 3000, 1500 * 900));
        dishes.add(new Dish("참치김밥", 600 * 3500, 1700 * 600));
        dishes.add(new Dish("돈가스김밥", 400 * 4000, 2500 * 400));
        dishes.add(new Dish("유부김밥", 500 * 4000, 1800 * 500));
        dishes.add(new Dish("김치김밥", 200 * 3500, 1700 * 200));
        dishes.add(new Dish("멸치김밥", 200 * 3500, 2000 * 200));

        int maxMaterialCost = material;

        narrowDownMenu(dishes, maxMaterialCost);

        tv_menu.setText(result);
        tv_delete_menu.setText(result2);
    }

    class Dish {
        private String name;
        private int value;
        private int weight;

        public Dish(String name, int value, int weight) {
            this.name = name;
            this.value = value;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }
    }

    public void narrowDownMenu(List<Dish> dishes, int maxWeight) {
        // Sort dishes based on value-to-weight ratio
        dishes.sort((dish1, dish2) -> Integer.compare(dish2.getValue() / dish2.getWeight(), dish1.getValue() / dish1.getWeight()));

        // Initialize variables
        int currentWeight = 0;
        int totalValue = 0;
        List<Dish> selectedDishes = new ArrayList<>();
        List<Dish> notselectedDishes = new ArrayList<>();

        // Iterate through the sorted list of dishes
        for (Dish dish : dishes) {
            if (currentWeight + dish.getWeight() <= maxWeight) {
                // Add dish to the selection
                selectedDishes.add(dish);
                currentWeight += dish.getWeight();
                totalValue += dish.getValue();
            }
            else{
                notselectedDishes.add(dish);
            }
        }

        // Display the selected dishes
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("메뉴이름").append("      총 수익").append("        총 재료비").append("\n");


        StringBuilder stringBuilder2 = new StringBuilder();
        for (Dish dish : selectedDishes) {
            stringBuilder2.append(dish.getName()).append("    " + dish.getValue()).append("    "+dish.getWeight()).append("\n");
        }
        result = stringBuilder.toString() + stringBuilder2.toString();

        StringBuilder stringBuilder3 = new StringBuilder();
        for(Dish dish : notselectedDishes) {
            stringBuilder3.append(dish.getName()).append("    " + dish.getValue()).append("    "+dish.getWeight()).append("\n");
        }
        result2 = stringBuilder.toString() + stringBuilder3.toString();
    }
}