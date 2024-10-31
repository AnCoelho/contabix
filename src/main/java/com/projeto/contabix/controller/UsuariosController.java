package br.poo.unipac.uberlandia.ematriculasserver.controller;

import br.poo.unipac.uberlandia.ematriculasserver.data.dto.EstatisticasDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.dto.MessagePeriodoDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.dto.UsuarioPeriodoDisciplinasDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.dto.UsuariosDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.entity.UsuariosEntity;
import br.poo.unipac.uberlandia.ematriculasserver.service.UsuariosService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController extends GenericController {

    @Autowired
    private UsuariosService usuariosService;

    @ApiOperation(value = "Busca por id")
    @GetMapping("/buscar-por-id")
    public UsuariosDTO findById(@RequestParam Long oidUsuarios) {

        return usuariosService.findById(oidUsuarios);
    }

    @ApiOperation(value = "Salva usuário")
    @PostMapping("/cadastrar-usuario")
    public UsuariosDTO save(@RequestBody UsuariosEntity usuario) {
        return usuariosService.save(usuario);
    }

    @ApiOperation(value = "Login usuário")
    @GetMapping("/login")
    public UsuariosDTO login(@RequestParam String nrCpf, @RequestParam String senha) {
        return usuariosService.login(nrCpf, senha);
    }

    @ApiOperation(value = "Valida o período do usuário")
    @GetMapping("/validar-periodo")
    public MessagePeriodoDTO login(@RequestParam String nrCpf) {
        return usuariosService.validaPeriodo(nrCpf);
    }

    @ApiOperation(value = "Pega todas informações do usuário")
    @GetMapping("/dados")
    public UsuarioPeriodoDisciplinasDTO getData(@RequestParam String nrCpf) {
        return usuariosService.getAllDataUser(nrCpf);
    }

    @ApiOperation(value = "Atualiza o usuário")
    @PutMapping("/atualizar-aluno")
    public UsuariosDTO update(@RequestBody UsuariosDTO usuario, @RequestParam String cpf) {
        return usuariosService.update(usuario, cpf);
    }

    @ApiOperation(value = "Deleta o usuário")
    @DeleteMapping("/deletar-aluno")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long oidUsuario) {
        usuariosService.deleteFisico(oidUsuario);
    }

    @ApiOperation(value = "Busca estatísticas")
    @GetMapping("/buscar-estatisticas")
    public EstatisticasDTO getEstatisticas() {

        return usuariosService.calcularEstatisticasNotasVestibular();
    }

}
