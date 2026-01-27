package entidades;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "credenciales")
public class Credenciales implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_credenciales")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 25)
	private String nombre;
	
	@Column(name = "password", nullable = false, length = 25)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Perfil perfil = Perfil.INVITADO;
	
	@OneToOne
	@JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
	private Persona persona;
	
	
	
	public Credenciales() {
		
	}
	public Credenciales(long id, String nombre, String password, Perfil perfil) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.perfil = perfil;
	}
	public Credenciales(String nombre, String password, Perfil perfil) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.perfil = perfil;
	}
	
	public Credenciales(String nombreAdministrador, String passwordAdministrador) {
		super();
		this.id = null;
		this.nombre = nombreAdministrador;
		this.password = passwordAdministrador;
		this.perfil = Perfil.ADMIN;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	
	
	

}
