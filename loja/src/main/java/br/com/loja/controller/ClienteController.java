package br.com.loja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.loja.model.Cliente;
import br.com.loja.repository.ClienteRepository;

@Controller
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/listar")
	public String listar(Model model, @ModelAttribute("message") String message) {
		List<Cliente> clientes = clienteRepository.findAll();
		model.addAttribute("clientes", clientes);
		model.addAttribute("message", message);
		return "clientes";
	}
	
	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente";
	}
	
	@GetMapping("/mostrar/{id}")
	public String mostrar(@PathVariable("id") Integer id, Model model){				
		Cliente cliente = clienteRepository.findById(id).orElseThrow(
			()->new IllegalArgumentException(id + " não consta na base de dados"));
		model.addAttribute("cliente", cliente);
		return "cliente";
	}
	
	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("cliente") Cliente cliente, RedirectAttributes redirectAttributes) {				
		clienteRepository.save(cliente);
		redirectAttributes.addFlashAttribute("message", "Registro salvo com sucesso");
		return "redirect:/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Integer id, Model model) {
		clienteRepository.deleteById(id);
		model.addAttribute("message", "Registro excluído com sucesso");
		return "redirect:/listar";
	}
}
