package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PedidoService{
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listar(){
        return this.pedidoRepository.findAll();
    }



    public Pedido salvar(Pedido pedido) {
        if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
            throw new RuntimeException("Não é possível criar um pedido sem itens.");
        }

        LocalDateTime hoje = LocalDateTime.now();
        pedido.setDataDoPedido(hoje);
        pedido.setDataDeEntregaDoPedido(hoje.plusDays(7));
        pedido.setStatus(true);

        pedido.getItems().forEach(itemPedido -> {
            itemPedido.setPedido(pedido);
        });


        return pedidoRepository.save(pedido);
    }

}
