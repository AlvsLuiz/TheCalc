package br.edu.ifsuldeminas.mch.calc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ifsuldeminas.mch.calc";

    private TextView textViewResultado;
    private TextView textViewUltimaExpressao;
    private String expressaoAtual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResultado = findViewById(R.id.textViewResultadoID);
        textViewUltimaExpressao = findViewById(R.id.textViewUltimaExpressaoID);

        // Números
        setNumericButton(R.id.buttonZeroID, "0");
        setNumericButton(R.id.buttonUmID, "1");
        setNumericButton(R.id.buttonDoisID, "2");
        setNumericButton(R.id.buttonTresID, "3");
        setNumericButton(R.id.buttonQuatroID, "4");
        setNumericButton(R.id.buttonCincoID, "5");
        setNumericButton(R.id.buttonSeisID, "6");
        setNumericButton(R.id.buttonSeteID, "7");
        setNumericButton(R.id.buttonOitoID, "8");
        setNumericButton(R.id.buttonNoveID, "9");

        // Operações
        setNumericButton(R.id.buttonSomaID, "+");
        setNumericButton(R.id.buttonSubtracaoID, "-");
        setNumericButton(R.id.buttonMultiplicacaoID, "*");
        setNumericButton(R.id.buttonDivisaoID, "/");
        setNumericButton(R.id.buttonVirgulaID, ".");

        // Botão de porcentagem (tratado manualmente)
        Button buttonPorcento = findViewById(R.id.buttonPorcentoID);
        buttonPorcento.setOnClickListener(v -> {
            try {
                double valor = Double.parseDouble(textViewResultado.getText().toString());
                valor = valor / 100.0;
                textViewUltimaExpressao.setText(expressaoAtual + "%");
                textViewResultado.setText(String.valueOf(valor));
                expressaoAtual = String.valueOf(valor);
            } catch (Exception e) {
                Log.d(TAG, "Erro porcentagem: " + e.getMessage());
            }
        });

        // Botão Reset (C)
        Button buttonReset = findViewById(R.id.buttonResetID);
        buttonReset.setOnClickListener(v -> {
            expressaoAtual = "";
            textViewResultado.setText("0");
            textViewUltimaExpressao.setText("");
        });

        // Botão Delete (D)
        Button buttonDelete = findViewById(R.id.buttonDeleteID);
        buttonDelete.setOnClickListener(v -> {
            if (!expressaoAtual.isEmpty()) {
                expressaoAtual = expressaoAtual.substring(0, expressaoAtual.length() - 1);
                textViewResultado.setText(expressaoAtual);
            }
        });

        // Botão Igual (=)
        Button buttonIgual = findViewById(R.id.buttonIgualID);
        buttonIgual.setOnClickListener(v -> {
            try {
                Calculable avaliador = new ExpressionBuilder(expressaoAtual).build();
                Double resultado = avaliador.calculate();

                textViewUltimaExpressao.setText(expressaoAtual);
                textViewResultado.setText(resultado.toString());

                // resultado vira a nova expressão
                expressaoAtual = resultado.toString();
            } catch (Exception e) {
                Log.d(TAG, "Erro cálculo: " + e.getMessage());
            }
        });
    }

    // Método auxiliar para números e operações
    private void setNumericButton(int id, String valor) {
        Button btn = findViewById(id);
        btn.setOnClickListener(v -> {
            expressaoAtual += valor;
            textViewResultado.setText(expressaoAtual);
        });
    }
}
