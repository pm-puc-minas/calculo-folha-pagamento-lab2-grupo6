package io.github.progmodular.gestaorh;

import io.github.progmodular.gestaorh.model.entities.Funcionario;
import io.github.progmodular.gestaorh.repository.FuncionarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Objects;

@SpringBootApplication
public class GestaoRhApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoRhApplication.class, args);
    }

    @Bean
    CommandLineRunner demoFuncionario(FuncionarioRepository repository) {
        return ignoredArgs -> {
            Objects.requireNonNull(ignoredArgs);
            System.out.println("\n==== DEMO FUNCIONARIO START ====");
            if (!repository.existsByEmail("ana@example.com")) {
                Funcionario f = new Funcionario();
                
                f.setName("Ana Souza");
                f.setEmail("ana@example.com");
                f.setPassword("123456");
                repository.save(f);
                System.out.println("Criado: Ana Souza <ana@example.com>");
            }

            if (!repository.existsByEmail("joao@example.com")) {
                Funcionario j = new Funcionario();
                j.setName("João Lima");
                j.setEmail("joao@example.com");
                j.setPassword("abc123");
                repository.save(j);
                System.out.println("Criado: João Lima <joao@example.com>");
            }

            long total = repository.count();
            System.out.println("Total de Funcionarios: " + total);
            repository.findAll().forEach(func ->
                System.out.println(func.getId() + " - " + func.getName() + " (" + func.getUserType() + ")")
            );

            var ana = repository.findByEmail("ana@example.com");
            if (ana != null) {
                System.out.println("Busca por email (ana@example.com): id=" + ana.getId() + ", nome=" + ana.getName());
            }

            System.out.println("==== DEMO FUNCIONARIO END ====\n");
        };
    }
}
