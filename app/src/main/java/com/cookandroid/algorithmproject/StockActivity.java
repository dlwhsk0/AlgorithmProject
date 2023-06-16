package com.cookandroid.algorithmproject;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockActivity extends AppCompatActivity {
    TextView tvCurrent, tvOrder;
    Button btnPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        tvCurrent = (TextView) findViewById(R.id.tvCur);
        tvOrder = (TextView) findViewById(R.id.tvOrd);
        btnPre = (Button) findViewById(R.id.btn);

        String[] stockStatusArray = currentStockList();
        String[] autoOrderArray = autoOrderPrediction();

        // 현재 재고 현황 문자열 배열을 하나의 string 변수에 넣고 텍스트뷰에 넣기
        String stockText = "";
        for (String stockStatus : stockStatusArray) {
            stockText += stockStatus + "\n";
        }
        tvCurrent.setText(stockText);
        tvCurrent.setMovementMethod(new ScrollingMovementMethod());
        System.out.println("현재 재고량: " + stockText);

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 예상 발주량 문자열 배열을 하나의 string 변수에 넣고 텍스트뷰에 넣기
                String orderText = "";
                for (String autoOrder : autoOrderArray) {
                    if(autoOrder != null) {
                        orderText += autoOrder + "\n";
                    }
                }
                tvOrder.setText(orderText);
                tvOrder.setMovementMethod(new ScrollingMovementMethod());
                System.out.println("예상 발주량: " + orderText);
            }
        });
    }

    // 재료 및 단위 리스트
    private Map<String, String> ingredientsList() {
        // 재료 및 단위 리스트
        // String[] ingredients = {"쌀", "김", "단무지", "계란", "당근", "맛살", "우엉", "참치"};
        Map<String, String> countName = new HashMap<>();
        countName.put("쌀", "kg");
        countName.put("김", "박스");
        countName.put("단무지", "박스");
        countName.put("계란", "판");
        countName.put("당근", "kg");
        countName.put("맛살", "박스");
        countName.put("우엉", "박스");
        countName.put("참치", "kg");

        return countName;
    }

    // 현재 재고량
    private Map<String, Integer> currentStockAmount() {
        // 현재 재고 수량
        Map<String, Integer> currentStock = new HashMap<>();
        currentStock.put("쌀", 10);
        currentStock.put("김", 2);
        currentStock.put("단무지", 10);
        currentStock.put("계란", 2);
        currentStock.put("당근", 1);
        currentStock.put("맛살", 3);
        currentStock.put("우엉", 2);
        currentStock.put("참치", 8);

        return currentStock;
    }

    // 현재 재고 현황을 단위와 함께 문자열 배열에 저장 후 반환
    private String[] currentStockList() {

        Map<String, String> countName = ingredientsList();
        Map<String, Integer> currentStock = currentStockAmount();

        // 현재 재고 현황을 문자열 배열에 저장
        String[] stockArray = new String[currentStock.size()];
        int i = 0;
        for (String key : currentStock.keySet()) {
            String stockQuantity = currentStock.get(key) + " " + countName.get(key);
            stockArray[i] = key + " : " + stockQuantity;
            i++;
        }

        return stockArray;
    }

    // 예상 발주량 계산 및 단위와 함께 문자열 배열에 저장 후 반환
    private String[] autoOrderPrediction() {
        // 재료 사용량
        Map<String, List<Integer>> usedData = new HashMap<>();
        usedData.put("쌀", Arrays.asList(110, 80, 90, 100, 80, 120, 100, 80, 120, 110, 90, 95));
        usedData.put("김", Arrays.asList(5, 6, 5, 7, 6, 7, 4, 5, 6, 7, 7, 7));
        usedData.put("단무지", Arrays.asList(8, 7, 9, 8, 10, 9, 4, 8, 7, 6, 5, 8));
        usedData.put("계란", Arrays.asList(7, 8, 8, 7, 9, 9, 9, 8, 10, 9, 4, 8));
        usedData.put("당근", Arrays.asList(6, 7, 6, 7, 8, 8, 6, 7, 4, 5, 6, 7));
        usedData.put("맛살", Arrays.asList(5, 6, 5, 7, 6, 7, 4, 5, 6, 7, 7, 7));
        usedData.put("우엉", Arrays.asList(11, 8, 9, 10, 8, 12, 10, 8, 12, 11, 9, 9));
        usedData.put("참치", Arrays.asList(6, 7, 6, 7, 8, 8, 6, 7, 4, 5, 6, 7));

        Map<String, String> countName = ingredientsList();
        Map<String, Integer> currentStock = currentStockAmount();

        String[] usedArray = new String[usedData.size()];
        int i = 0;
        for (String key : usedData.keySet()) {
            // 지난 6개월 동안의 평균 사용량 계산
            List<Integer> last6MonthsUsed = usedData.get(key).subList(usedData.get(key).size() - 6, usedData.get(key).size());
            int sum = 0;
            for (int data : last6MonthsUsed) {
                sum += data;
            }
            double averageUsed = sum / last6MonthsUsed.size();

            // 안전 재고 비율 설정 및 계산
            double safetyStockRatio = 0.2;
            double safetyStock = averageUsed * safetyStockRatio;

            // 예상 사용량 계산
            double expectedUsed = averageUsed + safetyStock;

            // 발주량 계산
            int currentStockQuantity = currentStock.get(key);
            double orderQuantity = expectedUsed - currentStockQuantity;

            // 발주량이 음수인 경우 0으로 설정
            if (orderQuantity > 0) {
                usedArray[i] = key + " : " + orderQuantity + " " + countName.get(key);
                i++;
            }
        }

        return usedArray;
    }

}