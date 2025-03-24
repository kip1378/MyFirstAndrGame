package com.example.myfirstgame3rdtry;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Button> buttons = new ArrayList<>();
    private int emptyTileIndex = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // 1️⃣ Создаём массив чисел 1-15 и одну пустую клетку (0)
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            numbers.add(i);
        }
        numbers.add(0); // ОДНА пустая клетка
        Collections.shuffle(numbers);

        // 2️⃣ Теперь загружаем кнопки из GridLayout и заполняем числами
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            if (gridLayout.getChildAt(i) instanceof Button) {
                Button btn = (Button) gridLayout.getChildAt(i);
                buttons.add(btn); // Добавляем кнопку в список

                int value = numbers.get(i); // Берём случайное число

                if (value == 0) {
                    btn.setText(""); // Последняя клетка остаётся пустой
                    emptyTileIndex = i; // Запоминаем, где пустая клетка
                } else {
                    btn.setText(String.valueOf(value));
                }

                // Добавляем обработчик нажатий
                final int index = i;
                btn.setOnClickListener(v -> moveTile(index));
            }
        }
    }

    private void moveTile(int clickedIndex) {
        // Проверяем, можно ли двигать кнопку (рядом ли пустая клетка)
        if (isAdjacent(clickedIndex, emptyTileIndex)) {
            Button clickedButton = buttons.get(clickedIndex);
            Button emptyButton = buttons.get(emptyTileIndex);

            // Если кликнули на пустую клетку – ничего не делаем
            if (clickedButton.getText().toString().equals("")) {
                return;
            }

            // Меняем тексты кнопок местами
            emptyButton.setText(clickedButton.getText());
            clickedButton.setText("");

            // Обновляем индекс пустой клетки
            emptyTileIndex = clickedIndex;
        }
    }

    private boolean isAdjacent(int index1, int index2) {          // Проверяем, можно ли двигать кнопку (находится ли рядом пустая клетка)
        int gridSize = 3;

        int row1 = index1 / gridSize, col1 = index1 % gridSize;
        int row2 = index2 / gridSize, col2 = index2 % gridSize;

        return (Math.abs(row1 - row2) == 1 && col1 == col2) || // Вверх-вниз
                (Math.abs(col1 - col2) == 1 && row1 == row2);   // Влево-вправо
    }
}


