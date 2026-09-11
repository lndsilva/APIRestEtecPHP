package br.com.etecia.apirestetecphp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private List<Produto> listaProdutos;

    public ProdutoAdapter(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.txtNome.setText(produto.getNome());
        holder.txtPreco.setText("ID: #" + produto.getId());

        // ITERAÇÃO COM A API: O Glide chama a URL da API PHP que cospe o BLOB binário
        Glide.with(holder.itemView.getContext())
                .load(produto.getUrlImagem())
                .placeholder(android.R.drawable.ic_menu_gallery) // Imagem enquanto carrega
                .error(android.R.drawable.ic_delete)           // Imagem se der erro (ex: sem foto)
                .into(holder.imgProduto);
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtPreco;
        ImageView imgProduto; // Declarando a ImageView

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeProduto);
            txtPreco = itemView.findViewById(R.id.txtPrecoProduto);
            imgProduto = itemView.findViewById(R.id.imgProduto); // Vinculando
        }
    }
}