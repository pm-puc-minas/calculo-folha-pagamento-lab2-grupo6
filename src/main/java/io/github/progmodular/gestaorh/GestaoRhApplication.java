package io.github.progmodular.gestaorh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GestaoRhApplication {


	public static void main(String[] args) {
		SpringApplication.run(GestaoRhApplication.class, args);
//        //Importe seu Repositório aqui
//         @Autowired
//         private FuncionarioRepository repository;
//
//        //Tente contar os registros:
//         long count = repository.count();
//         System.out.println("Conexão OK! Total de itens: " + count);
	}

}
