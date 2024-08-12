package br.com.financias.trabalho.service;

import br.com.financias.trabalho.model.Gastos;
import br.com.financias.trabalho.model.dao.ClienteDao;
import br.com.financias.trabalho.model.dao.GastosDao;
import br.com.financias.trabalho.model.dto.GastosDTO;
import br.com.financias.trabalho.model.enums.ECategoriaGastos;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.service.dto.GastosDashboardResponseDTO;
import br.com.financias.trabalho.service.dto.GastosResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GastosService {

    @Autowired
    GastosDao gastosDao;

    @Autowired
    ClienteDao clienteDao;

    public GastosResponseDTO registrarGastos(GastosDTO gastosDTO) {

        if(gastosDTO.getValor() < 0) {
            return new GastosResponseDTO(gastosDTO,null, "Não é possível cadastrar gastos negativos!", "gastos");
        }

        Gastos gastos = new Gastos();


        ECategoriaGastos categoria = ECategoriaGastos.valueOf(gastosDTO.getCategoria().toUpperCase());

        gastos.setCliente(clienteDao.findByLogin(gastosDTO.getLoginUser()));
        gastos.setValor(gastosDTO.getValor());
        gastos.setDescricao(gastosDTO.getDescricao());
        gastos.setECategoriaGastos(categoria);

        gastosDao.save(gastos);
        System.out.println(gastos.getCliente().getLogin() + "Adiciondo no usuario Cliente");

        return new GastosResponseDTO(new GastosDTO(),null, "Gasto registrado com sucesso", "gastos");


    }

    public void deleteById(Integer id) {

        gastosDao.deleteById(id);
    }

    public Optional<Gastos> FindById(Integer id) {
        return gastosDao.findById(id);
    }

    public GastosResponseDTO updateGastos(Gastos gastosDTO, Integer id) {

        Optional<Gastos> gastos = gastosDao.findById(id);

        ECategoriaGastos categoria = ECategoriaGastos.valueOf(String.valueOf(gastosDTO.getECategoriaGastos()));
        gastos.get().setECategoriaGastos(categoria);

        if(gastosDTO.getValor() < 0) {
            return new GastosResponseDTO(null, gastosDTO, "Não é possível cadastrar gastos negativos!", "alterar");
        }

        gastos.get().setValor(gastosDTO.getValor());
        gastos.get().setDescricao(gastosDTO.getDescricao());

        gastosDao.save(gastos.get());

        return new GastosResponseDTO(null, gastosDTO, "Cliente alterado com sucesso!", "redirect:/cliente/dashboard");
    }

    public GastosDashboardResponseDTO calculoDadosDashboard(List<Gastos> todosOsGastos) {

        double totalGastos = 0;
        double valorContas = 0;
        double valorSaude = 0;
        double valorComida = 0;
        double valorTransporte = 0;
        double valorInvestimento = 0;
        double valorDoacao = 0;
        double valorLazer = 0;
        double valorAssinaturas = 0;
        double valorEducacao = 0;
        double valorOutros = 0;

        for (Gastos gastos : todosOsGastos) {
            totalGastos += gastos.getValor();

            switch (gastos.getECategoriaGastos()) {
                case CONTAS:
                    valorContas += gastos.getValor();
                    break;
                case SAUDE:
                    valorSaude += gastos.getValor();
                    break;
                case COMIDA:
                    valorComida += gastos.getValor();
                    break;
                case TRANSPORTE:
                    valorTransporte += gastos.getValor();
                    break;
                case INVESTIMENTO:
                    valorInvestimento += gastos.getValor();
                    break;
                case DOACAO:
                    valorDoacao += gastos.getValor();
                    break;
                case LAZER:
                    valorLazer += gastos.getValor();
                    break;
                case ASSINATURAS:
                    valorAssinaturas += gastos.getValor();
                    break;
                case EDUCACAO:
                    valorEducacao += gastos.getValor();
                    break;
                case OUTROS:
                    valorOutros += gastos.getValor();
                    break;
            }
        }

        return new GastosDashboardResponseDTO(totalGastos, valorContas, valorSaude,
                valorComida, valorTransporte, valorInvestimento, valorDoacao, valorLazer,
                valorAssinaturas, valorEducacao, valorOutros, 0, 0);
    }

    public List<Gastos> retornaGastosPorCategoria(String login, String categoria) {

        Cliente cliente = clienteDao.findByLogin(login);

        List<Gastos> gastos = cliente.getGastos();

        List<Gastos> filtradoPorCategoria = gastos.stream()
                .filter(gasto -> gasto.getECategoriaGastos().toString().equals(categoria))
                .collect(Collectors.toList());

        return filtradoPorCategoria;
    }
}