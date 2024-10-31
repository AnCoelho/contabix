package br.poo.unipac.uberlandia.ematriculasserver.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosDTO {
    private Long oidUsuarios;
    private String nome;
    private String nrCpf;
    private Date dtNascimento;
    private String nmResponsavel;
    private Double notaVestibular;
    private String dsStatus;
    private String dsSenha;
    private Long oidPeriodo;
    private Double vrBolsa;
}
