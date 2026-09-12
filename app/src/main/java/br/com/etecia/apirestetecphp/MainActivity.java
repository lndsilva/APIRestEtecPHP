package br.com.etecia.apirestetecphp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnCarregarLista;
    private RecyclerView recyclerViewProdutos;
    private ProdutoAdapter produtoAdapter;
    private List<Produto> listaProdutos;

    // CORREÇÃO: URLs completas apontando para a sua pasta do XAMPP via IP do emulador
    private static final String URL_API_LISTA = "http://10.0.2";
    private static final String URL_API_IMAGEM_BASE = "http://10.0.2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa os componentes do Layout XML
        btnCarregarLista = findViewById(R.id.btnCarregarLista);
        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);

        // Inicializa a lista e configura o RecyclerView com o LayoutManager
        listaProdutos = new ArrayList<>();
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa o Adapter e vincula ao RecyclerView
        produtoAdapter = new ProdutoAdapter(listaProdutos);
        recyclerViewProdutos.setAdapter(produtoAdapter);

        // Configura o evento de clique do botão para buscar os dados da API
        btnCarregarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requisitarListaComVolley();
            }
        });
    }

    private void requisitarListaComVolley() {
        // Inicializa a fila de requisições do Volley
        RequestQueue fila = Volley.newRequestQueue(this);

        // Cria a requisição GET esperando um Array JSON como resposta
        JsonArrayRequest requisicaoArray = new JsonArrayRequest(Request.Method.GET, URL_API_LISTA, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray resposta) {
                try {
                    // Limpa os registros anteriores para evitar itens duplicados na tela
                    listaProdutos.clear();

                    // Percorre todos os objetos JSON retornados pela API PHP
                    for (int i = 0; i < resposta.length(); i++) {
                        JSONObject objetoJson = resposta.getJSONObject(i);

                        // Captura os dados textuais enviados pelo PHP (ID e Nome)
                        int id = objetoJson.getInt("id");
                        String nome = objetoJson.getString("nome");
                        double preco = 0.0; // Defina um valor ou capture se existir no banco

                        // MONTAGEM DINÂMICA DA URL: Interagindo com o script php que expõe o BLOB
                        String urlImagemCompleta = URL_API_IMAGEM_BASE + id;

                        // Instancia a classe de modelo e adiciona na lista do RecyclerView
                        Produto produto = new Produto(id, nome, preco, urlImagemCompleta);
                        listaProdutos.add(produto);
                    }

                    // Notifica o Adapter que a lista mudou para atualizar a interface gráfica
                    produtoAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Dados e imagens carregados!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Erro ao interpretar JSON", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError erro) {
                // Trata erros de conexão de rede ou URL inválida
                Toast.makeText(MainActivity.this, "Erro de rede: " + erro.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adiciona a requisição na fila do Volley para execução imediata
        fila.add(requisicaoArray);
    }
}
