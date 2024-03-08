/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao;

import controle.ControleAluno;
import controle.ControleDocente;
import controle.ControleFuncionario;
import controle.IControleCadastro;
import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;
import javax.swing.JComboBox;
import modelo.Curso;
import util.DialogBoxUtils;
import util.NumberUtils;

/**
 *
 * @author Andre Leiria
 */
public class TelaCadastroPessoa extends TelaCadastro {

    /**
     * Creates new form TelaCadastroPessoa
     */
    public TelaCadastroPessoa(IControleCadastro controle) {
        super(controle);
        initComponents();
        setLocationRelativeTo(null);
        buttonGroup1.add(jRB_Masculino);
        buttonGroup1.add(jRB_Feminino);
        inicializarComponentesTela();
    }
    
    public static boolean verificarDuplicidade(JComboBox comboBox, Object item){
        for(int i = 0; i < comboBox.getItemCount(); i++ ){
            Object currentItem = comboBox.getItemAt(i);
            if(currentItem.equals(item)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void inicializarComponentesTela() {
        jTF_Nome.setText("");
        jTF_Cpf.setText("");
        jTF_Email.setText("");

        buttonGroup1.clearSelection();

        jS_Dia.setValue(1);
        jS_Mes.setValue(1);
        jS_Ano.setValue(2004);

        jCB_Cidade.setSelectedItem("Cascavel");
        jTF_Rua.setText("");
        jTF_Numero.setText("");

        if (controle instanceof ControleAluno) {
            super.setTitle("Cadastro de Aluno");
            jLabelTitulo.setText("Cadastro de Aluno");
            jPanelFuncionario.setVisible(false);
            ControleAluno controleAluno = (ControleAluno) controle;

            for (String nomeCurso : controleAluno.getControleCurso().getNomesCursos()) {
                if(!verificarDuplicidade(jCB_Curso, nomeCurso))
               jCB_Curso.addItem(nomeCurso);
            }
            jCB_Curso.setSelectedIndex(0);
            
            jTF_RA.setText("");
            jS_Matricula_Dia.setValue(1);
            jS_Matricula_Mes.setValue(1);
            jS_Matricula_Ano.setValue(2004);
            jCB_Situacao.setSelectedIndex(0);
        } else {
            jPanelDadosAluno.setVisible(false);
            jTF_CTPS.setText("");
            jTF_Salario.setText("");
            jTF_Formacao.setText("");
            if (controle instanceof ControleDocente) {
                super.setTitle("Cadastro de Docente");
                jLabelTitulo.setText("Cadastro de Docente");
                jTF_Formacao.setVisible(true);
            }
            else{
                super.setTitle("Cadastro de Funcionário");
                jLabelTitulo.setText("Cadastro de Funcionário");
                jTF_Formacao.setVisible(false);
            }
        }

    }

    @Override
    /**
     * *
     * Setar os compenentes da tela com as informações passadas por parametro no
     * vetor dados. Cada posição do vetor representa uma informação específica.
     * [0] - nome [1] - cpf [2] - email [3] - genero [4] - data de nascimento
     */
    public void setarDadosTela(HashMap<String, Object> dados) {
        if (dados == null || dados.isEmpty()) {
            DialogBoxUtils.exibirMensagemDeErro("Erro", "Erro ao setar dados na tela");
        }

        jTF_Nome.setText((String) dados.get("nome"));
        jTF_Cpf.setText((String) dados.get("cpf"));
        jTF_Email.setText((String) dados.get("email"));

        if (((String) dados.get("genero")).equalsIgnoreCase("Masculino")) {
            jRB_Masculino.setSelected(true);
        } else if (((String) dados.get("genero")).equalsIgnoreCase("Feminino")) {
            jRB_Feminino.setSelected(true);
        }

        if (dados.get("datanascimento") != null) {
            LocalDate dataNascimento = (LocalDate) dados.get("datanascimento");
            jS_Dia.setValue(dataNascimento.getDayOfMonth());
            jS_Mes.setValue(dataNascimento.getMonthValue());
            jS_Ano.setValue(dataNascimento.getYear());
        }

        jCB_Cidade.setSelectedItem((String) dados.get("cidade"));
        jTF_Rua.setText((String) dados.get("rua"));
        jTF_Numero.setText((String) dados.get("numero"));

        if (controle instanceof ControleAluno) {
            setarDadosTelaAluno(dados);
        } else if (controle instanceof ControleFuncionario) {
            setarDadosTelaFuncionario(dados);
            jTF_Formacao.setVisible(false);
        } else if (controle instanceof ControleDocente) {
            setarDadosTelaDocente(dados);
            jTF_Formacao.setVisible(true);
        }
    }

    public void setarDadosTelaAluno(HashMap<String, Object> dados) {
        jTF_RA.setText((String) dados.get("ra"));
        jCB_Curso.setSelectedItem(((Curso) dados.get("curso")).getNome());
        if (dados.get("datamatricula") != null) {
            
            Date data = (Date) dados.get("datamatricula");
            LocalDate dataMatricula = data.toLocalDate();
            //LocalDate dataMatricula = (LocalDate) dados.get("datamatricula");
                      
            jS_Matricula_Dia.setValue(dataMatricula.getDayOfMonth());
            jS_Matricula_Mes.setValue(dataMatricula.getMonthValue());
            jS_Matricula_Ano.setValue(dataMatricula.getYear());
        }
        jCB_Situacao.setSelectedItem((String) dados.get("situacao"));
    }

    public void setarDadosTelaFuncionario(HashMap<String, Object> dados) {
        jTF_CTPS.setText((String) dados.get("ctps"));
        jTF_Salario.setText(NumberUtils.formatarValor((double) dados.get("salario"), 2));
    }

    public void setarDadosTelaDocente(HashMap<String, Object> dados) {
        setarDadosTelaFuncionario(dados);
        jTF_Formacao.setText((String) dados.get("formacao"));
    }

    /**
     * *
     * Retorna um HashMap com as informações de cadastro da tela Cada informação
     * está associada a uma chave (que representa o nome do campo).
     *
     *
     * @return HashMap com as informações
     */
    @Override
    public HashMap<String, Object> getDadosTela() {
        HashMap<String, Object> dados = new HashMap<>();
        String genero = jRB_Feminino.isSelected() ? "Feminino" : "Masculino";
        int dia = (int) jS_Dia.getValue();
        int mes = (int) jS_Mes.getValue();
        int ano = (int) jS_Ano.getValue();
        LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

        dados.put("nome", jTF_Nome.getText());
        dados.put("cpf", jTF_Cpf.getText());
        dados.put("email", jTF_Email.getText());
        dados.put("genero", genero);
        dados.put("datanascimento", dataNascimento);

        dados.put("cidade", jCB_Cidade.getSelectedItem());
        dados.put("rua", jTF_Rua.getText());
        dados.put("numero", jTF_Numero.getText());

        if (controle instanceof ControleAluno) {
            ControleAluno controleAluno = (ControleAluno) controle;
            int indexCurso = jCB_Curso.getSelectedIndex() - 1;
            Curso cur = controleAluno.getControleCurso().getCursoSelecionado(indexCurso);
            int dia_matricula = (int) jS_Dia.getValue();
            int mes_matricula = (int) jS_Mes.getValue();
            int ano_matricula = (int) jS_Ano.getValue();
            LocalDate dataMatricula = LocalDate.of(ano_matricula, mes_matricula, dia_matricula);

            dados.put("ra", jTF_RA.getText());
            dados.put("curso", cur);
            dados.put("datamatricula", dataMatricula);
            dados.put("situacao", jCB_Situacao.getSelectedItem().toString());

        } else if (controle instanceof ControleFuncionario) {
            dados.put("ctps", jTF_CTPS.getText());
            dados.put("salario", jTF_Salario.getText());

            if (controle instanceof ControleDocente) {
                dados.put("formacao", jTF_Formacao.getText());
            }
        }

        return dados;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabelTitulo = new javax.swing.JLabel();
        jPanelDadosPessoais = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTF_Nome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTF_Cpf = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTF_Email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jRB_Feminino = new javax.swing.JRadioButton();
        jRB_Masculino = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jS_Dia = new javax.swing.JSpinner();
        jS_Mes = new javax.swing.JSpinner();
        jS_Ano = new javax.swing.JSpinner();
        jPanelEndereco = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTF_Rua = new javax.swing.JTextField();
        jCB_Cidade = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jTF_Numero = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanelDadosAluno = new javax.swing.JPanel();
        jCB_Curso = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTF_RA = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jS_Matricula_Dia = new javax.swing.JSpinner();
        jS_Matricula_Mes = new javax.swing.JSpinner();
        jS_Matricula_Ano = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jCB_Situacao = new javax.swing.JComboBox<>();
        jB_Cancelar = new javax.swing.JButton();
        jB_Salvar = new javax.swing.JButton();
        jPanelFuncionario = new javax.swing.JPanel();
        jTF_CTPS = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTF_Salario = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTF_Formacao = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Pessoa");
        setBackground(new java.awt.Color(0, 102, 102));

        jLabelTitulo.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(0, 102, 255));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Cadastro de Pessoa");
        jLabelTitulo.setToolTipText("");

        jPanelDadosPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nome:");

        jTF_Nome.setToolTipText("");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("CPF:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("email:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Gênero:");

        jRB_Feminino.setText("Feminino");

        jRB_Masculino.setText("Masculino");
        jRB_Masculino.setToolTipText("");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Data Nasc:");

        jS_Dia.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));

        jS_Mes.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        jS_Ano.setModel(new javax.swing.SpinnerNumberModel(2005, 1950, 2023, 1));

        jPanelEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Endereço", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Cidade:");

        jTF_Rua.setToolTipText("");

        jCB_Cidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " Selecione uma cidade", " Abatiá", " Adrianópolis", " Agudos do Sul", " Almirante Tamandaré", " Altamira do Paraná", " Alto Paraíso", " Alto Paraná", " Alto Piquiri", " Altônia", " Alvorada do Sul", " Amaporã", " Ampére", " Anahy", " Andirá", " Ângulo", " Antonina", " Antônio Olinto", " Apucarana", " Arapongas", " Arapoti", " Arapuã", " Araruna", " Araucária", " Ariranha do Ivaí", " Assaí", " Assis Chateaubriand", " Astorga", " Atalaia", " Balsa Nova", " Bandeirantes", " Barbosa Ferraz", " Barra do Jacaré", " Barracão", " Bela Vista da Caroba", " Bela Vista do Paraíso", " Bituruna", " Boa Esperança", " Boa Esperança do Iguaçu", " Boa Ventura de São Roque", " Boa Vista da Aparecida", " Bocaiuva do Sul", " Bom Jesus do Sul", " Bom Sucesso", " Bom Sucesso do Sul", " Borrazópolis", " Braganey", " Brasilândia do Sul", " Cafeara", " Cafelândia", " Cafezal do Sul", " Califórnia", " Cambará", " Cambé", " Cambira", " Campina da Lagoa", " Campina do Simão", " Campina Grande do Sul", " Campo Bonito", " Campo do Tenente", " Campo Largo", " Campo Magro", " Campo Mourão", " Cândido de Abreu", " Candói", " Cantagalo", " Capanema", " Capitão Leônidas Marques", " Carambeí", " Carlópolis", " Cascavel", " Castro", " Catanduvas", " Centenário do Sul", " Cerro Azul", " Céu Azul", " Chopinzinho", " Cianorte", " Cidade Gaúcha", " Clevelândia", " Colombo", " Colorado", " Congonhinhas", " Conselheiro Mairinck", " Contenda", " Corbélia", " Cornélio Procópio", " Coronel Domingos Soares", " Coronel Vivida", " Corumbataí do Sul", " Cruz Machado", " Cruzeiro do Iguaçu", " Cruzeiro do Oeste", " Cruzeiro do Sul", " Cruzmaltina", " Curitiba", " Curiúva", " Diamante do Norte", " Diamante do Sul", " Diamante D'Oeste", " Dois Vizinhos", " Douradina", " Doutor Camargo", " Doutor Ulysses", " Enéas Marques", " Engenheiro Beltrão", " Entre Rios do Oeste", " Esperança Nova", " Espigão Alto do Iguaçu", " Farol", " Faxinal", " Fazenda Rio Grande", " Fênix", " Fernandes Pinheiro", " Figueira", " Flor da Serra do Sul", " Floraí", " Floresta", " Florestópolis", " Flórida", " Formosa do Oeste", " Foz do Iguaçu", " Foz do Jordão", " Francisco Alves", " Francisco Beltrão", " General Carneiro", " Godoy Moreira", " Goioerê", " Goioxim", " Grandes Rios", " Guaíra", " Guairaçá", " Guamiranga", " Guapirama", " Guaporema", " Guaraci", " Guaraniaçu", " Guarapuava", " Guaraqueçaba", " Guaratuba", " Honório Serpa", " Ibaiti", " Ibema", " Ibiporã", " Icaraíma", " Iguaraçu", " Iguatu", " Imbaú", " Imbituva", " Inácio Martins", " Inajá", " Indianópolis", " Ipiranga", " Iporã", " Iracema do Oeste", " Irati", " Iretama", " Itaguajé", " Itaipulândia", " Itambaracá", " Itambé", " Itapejara d'Oeste", " Itaperuçu", " Itaúna do Sul", " Ivaí", " Ivaiporã", " Ivaté", " Ivatuba", " Jaboti", " Jacarezinho", " Jaguapitã", " Jaguariaíva", " Jandaia do Sul", " Janiópolis", " Japira", " Japurá", " Jardim Alegre", " Jardim Olinda", " Jataizinho", " Jesuítas", " Joaquim Távora", " Juranda", " Jussara", " Kaloré", " Lapa", " Laranjal", " Laranjeiras do Sul", " Leópolis", " Lidianópolis", " Lindoeste", " Loanda", " Lobato", " Londrina", " Luiziana", " Lunardelli", " Lupionópolis", " Mallet", " Mamborê", " Mandaguaçu", " Mandaguari", " Mandirituba", " Manfrinópolis", " Mangueirinha", " Manoel Ribas", " Marechal Cândido Rondon", " Maria Helena", " Marialva", " Marilândia do Sul", " Marilena", " Mariluz", " Maringá", " Mariópolis", " Maripá", " Marmeleiro", " Marquinho", " Marumbi", " Matelândia", " Matinhos", " Mato Rico", " Mauá da Serra", " Medianeira", " Mercedes", " Mirador", " Miraselva", " Missal", " Moreira Sales", " Morretes", " Munhoz de Melo", " Nossa Senhora das Graças", " Nova Aliança do Ivaí", " Nova América da Colina", " Nova Aurora", " Nova Esperança", " Nova Esperança do Sudoeste", " Nova Fátima", " Nova Laranjeiras", " Nova Londrina", " Nova Olímpia", " Nova Prata do Iguaçu", " Nova Santa Bárbara", " Nova Santa Rosa", " Nova Tebas", " Novo Itacolomi", " Ortigueira", " Ourizona", " Ouro Verde do Oeste", " Paiçandu", " Palmas", " Palmeira", " Palmital", " Palotina", " Paraíso do Norte", " Paranacity", " Paranaguá", " Paranapoema", " Paranavaí", " Pato Bragado", " Pato Branco", " Paula Freitas", " Paulo Frontin", " Peabirú", " Perobal", " Pérola", " Pérola d'Oeste", " Piên", " Pinhais", " Pinhal de São Bento", " Pinhalão", " Pinhão", " Piraí do Sul", " Piraquara", " Pitanga", " Pitangueiras", " Planaltina do Paraná", " Planalto", " Ponta Grossa", " Pontal do Paraná", " Porecatu", " Porto Amazonas", " Porto Barreiro", " Porto Rico", " Porto Vitória", " Prado Ferreira", " Pranchita", " Presidente Castelo Branco", " Primeiro de Maio", " Prudentópolis", " Quarto Centenário", " Quatiguá", " Quatro Barras", " Quatro Pontes", " Quedas do Iguaçu", " Querência do Norte", " Quinta do Sol", " Quitandinha", " Ramilândia", " Rancho Alegre", " Rancho Alegre D'Oeste", " Realeza", " Rebouças", " Renascença", " Reserva", " Reserva do Iguaçu", " Ribeirão Claro", " Ribeirão do Pinhal", " Rio Azul", " Rio Bom", " Rio Bonito do Iguaçu", " Rio Branco do Ivaí", " Rio Branco do Sul", " Rio Negro", " Rolândia", " Roncador", " Rondon", " Rosário do Ivaí", " Sabáudia", " Salgado Filho", " Salto do Itararé", " Salto do Lontra", " Santa Amélia", " Santa Cecília do Pavão", " Santa Cruz de Monte Castelo", " Santa Fé", " Santa Helena", " Santa Inês", " Santa Isabel do Ivaí", " Santa Izabel do Oeste", " Santa Lúcia", " Santa Maria do Oeste", " Santa Mariana", " Santa Mônica", " Santa Tereza do Oeste", " Santa Terezinha de Itaipu", " Santana do Itararé", " Santo Antônio da Platina", " Santo Antônio do Caiuá", " Santo Antônio do Paraíso", " Santo Antônio do Sudoeste", " Santo Inácio", " São Carlos do Ivaí", " São Jerônimo da Serra", " São João", " São João do Caiuá", " São João do Ivaí", " São João do Triunfo", " São Jorge do Ivaí", " São Jorge do Patrocínio", " São Jorge d'Oeste", " São José da Boa Vista", " São José das Palmeiras", " São José dos Pinhais", " São Manoel do Paraná", " São Mateus do Sul", " São Miguel do Iguaçu", " São Pedro do Iguaçu", " São Pedro do Ivaí", " São Pedro do Paraná", " São Sebastião da Amoreira", " São Tomé", " Sapopema", " Sarandi", " Saudade do Iguaçu", " Sengés", " Serranópolis do Iguaçu", " Sertaneja", " Sertanópolis", " Siqueira Campos", " Sulina", " Tamarana", " Tamboara", " Tapejara", " Tapira", " Teixeira Soares", " Telêmaco Borba", " Terra Boa", " Terra Rica", " Terra Roxa", " Tibagi", " Tijucas do Sul", " Toledo", " Tomazina", " Três Barras do Paraná", " Tunas do Paraná", " Tuneiras do Oeste", " Tupãssi", " Turvo", " Ubiratã", " Umuarama", " União da Vitória", " Uniflor", " Uraí", " Ventania", " Vera Cruz do Oeste", " Verê", " Virmond", " Vitorino", " Wenceslau Braz", " Xambrê", "Jundiaí do Sul", "Nova Cantu" }));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Rua:");

        jTF_Numero.setToolTipText("");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Número:");

        javax.swing.GroupLayout jPanelEnderecoLayout = new javax.swing.GroupLayout(jPanelEndereco);
        jPanelEndereco.setLayout(jPanelEnderecoLayout);
        jPanelEnderecoLayout.setHorizontalGroup(
            jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_Numero))
                    .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCB_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_Rua)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEnderecoLayout.setVerticalGroup(
            jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jCB_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTF_Rua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTF_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelDadosPessoaisLayout = new javax.swing.GroupLayout(jPanelDadosPessoais);
        jPanelDadosPessoais.setLayout(jPanelDadosPessoaisLayout);
        jPanelDadosPessoaisLayout.setHorizontalGroup(
            jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTF_Nome)
                    .addComponent(jTF_Cpf)
                    .addComponent(jTF_Email)
                    .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(jRB_Feminino, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRB_Masculino, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(jS_Dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jS_Mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jS_Ano, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDadosPessoaisLayout.setVerticalGroup(
            jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTF_Cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTF_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jRB_Feminino)
                    .addComponent(jRB_Masculino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jS_Dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jS_Mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jS_Ano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanelEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanelDadosAluno.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Aluno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N

        jCB_Curso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o Curso" }));
        jCB_Curso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_CursoActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Curso:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("RA:");

        jTF_RA.setToolTipText("");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Data Matri.:");

        jS_Matricula_Dia.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        jS_Matricula_Dia.setMinimumSize(new java.awt.Dimension(50, 22));
        jS_Matricula_Dia.setPreferredSize(new java.awt.Dimension(58, 22));

        jS_Matricula_Mes.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));
        jS_Matricula_Mes.setMinimumSize(new java.awt.Dimension(50, 22));
        jS_Matricula_Mes.setPreferredSize(new java.awt.Dimension(58, 22));

        jS_Matricula_Ano.setModel(new javax.swing.SpinnerNumberModel(2005, 1950, 2023, 1));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Situação:");

        jCB_Situacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Andamento", "Matrícula Trancada", "Matrícula Cancelada", "Concuído", "Transfêrencia" }));

        javax.swing.GroupLayout jPanelDadosAlunoLayout = new javax.swing.GroupLayout(jPanelDadosAluno);
        jPanelDadosAluno.setLayout(jPanelDadosAlunoLayout);
        jPanelDadosAlunoLayout.setHorizontalGroup(
            jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosAlunoLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTF_RA)
                    .addComponent(jCB_Curso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelDadosAlunoLayout.createSequentialGroup()
                        .addComponent(jS_Matricula_Dia, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jS_Matricula_Mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jS_Matricula_Ano, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCB_Situacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelDadosAlunoLayout.setVerticalGroup(
            jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosAlunoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_RA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCB_Curso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jS_Matricula_Dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jS_Matricula_Mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jS_Matricula_Ano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosAlunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jCB_Situacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jB_Cancelar.setBackground(new java.awt.Color(219, 61, 9));
        jB_Cancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jB_Cancelar.setForeground(java.awt.SystemColor.control);
        jB_Cancelar.setText("Cancelar");
        jB_Cancelar.setPreferredSize(new java.awt.Dimension(100, 27));
        jB_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_CancelarActionPerformed(evt);
            }
        });

        jB_Salvar.setBackground(new java.awt.Color(0, 200, 76));
        jB_Salvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jB_Salvar.setForeground(java.awt.SystemColor.control);
        jB_Salvar.setText("Salvar");
        jB_Salvar.setPreferredSize(new java.awt.Dimension(100, 27));
        jB_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SalvarActionPerformed(evt);
            }
        });

        jPanelFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Funcionário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N

        jTF_CTPS.setToolTipText("");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("CTPS:");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Salário:");

        jTF_Salario.setToolTipText("");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Formação:");

        jTF_Formacao.setToolTipText("");

        javax.swing.GroupLayout jPanelFuncionarioLayout = new javax.swing.GroupLayout(jPanelFuncionario);
        jPanelFuncionario.setLayout(jPanelFuncionarioLayout);
        jPanelFuncionarioLayout.setHorizontalGroup(
            jPanelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFuncionarioLayout.createSequentialGroup()
                .addGroup(jPanelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFuncionarioLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_CTPS))
                    .addGroup(jPanelFuncionarioLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_Salario))
                    .addGroup(jPanelFuncionarioLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_Formacao)))
                .addContainerGap())
        );
        jPanelFuncionarioLayout.setVerticalGroup(
            jPanelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_CTPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Formacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanelDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanelDadosAluno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanelFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jB_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2)))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelDadosAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jB_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_CancelarActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jB_CancelarActionPerformed

    private void jB_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SalvarActionPerformed

        if (editarDados) {
            controle.editar(getDadosTela());
        } else {
            controle.salvar(getDadosTela());
        }
        controle.atualizarTabelaTelaListagem();
        setVisible(false);
    }//GEN-LAST:event_jB_SalvarActionPerformed

    private void jCB_CursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_CursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCB_CursoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jB_Cancelar;
    private javax.swing.JButton jB_Salvar;
    private javax.swing.JComboBox<String> jCB_Cidade;
    private javax.swing.JComboBox<String> jCB_Curso;
    private javax.swing.JComboBox<String> jCB_Situacao;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanelDadosAluno;
    private javax.swing.JPanel jPanelDadosPessoais;
    private javax.swing.JPanel jPanelEndereco;
    private javax.swing.JPanel jPanelFuncionario;
    private javax.swing.JRadioButton jRB_Feminino;
    private javax.swing.JRadioButton jRB_Masculino;
    private javax.swing.JSpinner jS_Ano;
    private javax.swing.JSpinner jS_Dia;
    private javax.swing.JSpinner jS_Matricula_Ano;
    private javax.swing.JSpinner jS_Matricula_Dia;
    private javax.swing.JSpinner jS_Matricula_Mes;
    private javax.swing.JSpinner jS_Mes;
    private javax.swing.JTextField jTF_CTPS;
    private javax.swing.JTextField jTF_Cpf;
    private javax.swing.JTextField jTF_Email;
    private javax.swing.JTextField jTF_Formacao;
    private javax.swing.JTextField jTF_Nome;
    private javax.swing.JTextField jTF_Numero;
    private javax.swing.JTextField jTF_RA;
    private javax.swing.JTextField jTF_Rua;
    private javax.swing.JTextField jTF_Salario;
    // End of variables declaration//GEN-END:variables

}
