package br.com.financias.trabalho;

import br.com.financias.trabalho.config.SecurityConfig;
import br.com.financias.trabalho.model.dao.AdministradorDao;
import br.com.financias.trabalho.model.dao.ClienteDao;
import br.com.financias.trabalho.model.dao.ModeradorDao;
import br.com.financias.trabalho.model.usuarios.Administrador;
import br.com.financias.trabalho.model.usuarios.Cliente;
import br.com.financias.trabalho.model.usuarios.Moderador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TrabalhoApplication implements CommandLineRunner {

	@Autowired
	AdministradorDao ad;

	@Autowired
	SecurityConfig sc;

	@Autowired
	ClienteDao cd;

	@Autowired
	ModeradorDao md;

	@Autowired
	PasswordEncoder pass;

	@Override
	public void run(String... args) throws Exception {

		System.out.println("#### SERVER STARTER ####");

		Cliente c1 = new Cliente();
		c1.setEmail("cliente@cliente.com");
		c1.setNome("Cliente");
		c1.setSenha(pass.encode("123"));
		c1.setLogin("cliente");
		cd.save(c1);

		System.out.println("#### Cliente salvo ####");

		Moderador m1 = new Moderador();
		m1.setEmail("moderador@moderador.com");
		m1.setNome("Moderador");
		m1.setSenha(pass.encode("123"));
		m1.setLogin("moderador");
		md.save(m1);

		System.out.println("#### Moderador salvo ####");

		Administrador adm = new Administrador();
		adm.setNome("Administrador");
		adm.setSenha(pass.encode("123"));
		adm.setEmail("admin@admin.com");
		adm.setLogin("admin");
		ad.save(adm);

		System.out.println("#### Administrador salvo ####");
	}

	public static void main(String[] args) {
		SpringApplication.run(TrabalhoApplication.class, args);
	}

}

