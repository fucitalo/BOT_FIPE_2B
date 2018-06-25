
public class Carro {
	
	private String nome;
	private String codigo;
	private String veiculo;
	private String valor;
	private String ano;
	
	public Carro(String nome, String codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}
		
	/* Carro(String nome, String veiculo, String ano, String valor) {
		this.nome = nome;
		this.veiculo = veiculo;
		this.valor = valor;
		this.ano = ano;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}*/
	
	public Carro() {
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(nome).append("," + codigo).toString();
	}

}
