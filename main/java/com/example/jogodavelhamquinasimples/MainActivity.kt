package com.example.jogodavelhamquinasimples

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.jogodavelhamquinasimples.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // Variável de ligação para acesso às views
    private lateinit var binding: ActivityMainBinding

    // Vetor bidimensional que representará o tabuleiro de jogo
    val tabuleiro = arrayOf(
        arrayOf("", "", ""),
        arrayOf("", "", ""),
        arrayOf("", "", "")
    )

    // Nível de dificuldade: 0 = fácil, 1 = médio, 2 = difícil
    var nivelDificuldade = 0

    // Método onCreate que é chamado quando a Activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla o layout usando o binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurações de dificuldade
        binding.buttonFacil.setOnClickListener { nivelDificuldade = 0 }
        binding.buttonMedio.setOnClickListener { nivelDificuldade = 1 }
        binding.buttonDificil.setOnClickListener { nivelDificuldade = 2 }

        // Configurações dos botões do tabuleiro
        val buttons = arrayOf(
            binding.buttonZero, binding.buttonUm, binding.buttonDois,
            binding.buttonTres, binding.buttonQuatro, binding.buttonCinco,
            binding.buttonSeis, binding.buttonSete, binding.buttonOito
        )

        for (button in buttons) {
            button.setOnClickListener { buttonClick(it) }
            button.setBackgroundColor(resources.getColor(R.color.purple_200, null))
        }
    }

    // Função que será chamada quando um botão for clicado
    fun buttonClick(view: View) {
        val buttonSelecionado = view as Button
        buttonSelecionado.text = "X"
        buttonSelecionado.setTextColor(resources.getColor(R.color.green, null))
        buttonSelecionado.isEnabled = false

        // Atualiza o tabuleiro com "X" na posição correspondente ao botão clicado
        when (buttonSelecionado.id) {
            binding.buttonZero.id -> tabuleiro[0][0] = "X"
            binding.buttonUm.id -> tabuleiro[0][1] = "X"
            binding.buttonDois.id -> tabuleiro[0][2] = "X"
            binding.buttonTres.id -> tabuleiro[1][0] = "X"
            binding.buttonQuatro.id -> tabuleiro[1][1] = "X"
            binding.buttonCinco.id -> tabuleiro[1][2] = "X"
            binding.buttonSeis.id -> tabuleiro[2][0] = "X"
            binding.buttonSete.id -> tabuleiro[2][1] = "X"
            binding.buttonOito.id -> tabuleiro[2][2] = "X"
        }

        // Verifica se o jogador ganhou
        if (verificarVitoria("X")) {
            desativarTodosBotoes()
            binding.textViewResultado.text = "Você ganhou!"
            return
        }

        // Verifica se há empate após a jogada do jogador
        if (verificarEmpate()) {
            desativarTodosBotoes()
            binding.textViewResultado.text = "Empate!"
            return
        }

        // Movimento do computador de acordo com o nível de dificuldade
        when (nivelDificuldade) {
            0 -> movimentoComputadorFacil()
            1 -> movimentoComputadorMedio()
            2 -> movimentoComputadorDificil()
        }

        // Verifica se o computador ganhou
        if (verificarVitoria("O")) {
            desativarTodosBotoes()
            binding.textViewResultado.text = "Você perdeu!"
            return
        }

        // Verifica se há empate após a jogada do computador
        if (verificarEmpate()) {
            desativarTodosBotoes()
            binding.textViewResultado.text = "Empate!"
        }
    }

    // Funções de movimento do computador para cada nível de dificuldade
    private fun movimentoComputadorFacil() {
        var rX: Int
        var rY: Int
        while (true) {
            rX = Random.nextInt(0, 3)
            rY = Random.nextInt(0, 3)
            if (tabuleiro[rX][rY].isEmpty()) {
                tabuleiro[rX][rY] = "O"
                atualizarButton(rX, rY, "O")
                break
            }
        }
    }

    private fun movimentoComputadorMedio() {
        if (!tentarBloquear("X")) {
            movimentoComputadorFacil()
        }
    }

    private fun movimentoComputadorDificil() {
        if (!tentarGanhar("O")) {
            if (!tentarBloquear("X")) {
                movimentoComputadorFacil()
            }
        }
    }

    // Tenta bloquear a vitória do jogador ou ganhar o jogo
    private fun tentarBloquear(jogador: String): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (tabuleiro[i][j].isEmpty()) {
                    tabuleiro[i][j] = jogador
                    if (verificarVitoria(jogador)) {
                        tabuleiro[i][j] = "O"
                        atualizarButton(i, j, "O")
                        return true
                    } else {
                        tabuleiro[i][j] = ""
                    }
                }
            }
        }
        return false
    }

    private fun tentarGanhar(jogador: String): Boolean {
        return tentarBloquear(jogador)
    }

    // Atualiza o texto e o estado do botão correspondente no layout
    private fun atualizarButton(rX: Int, rY: Int, texto: String) {
        val posicao = rX * 3 + rY
        when (posicao) {
            0 -> {
                binding.buttonZero.text = texto
                binding.buttonZero.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonZero.isEnabled = false
            }
            1 -> {
                binding.buttonUm.text = texto
                binding.buttonUm.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonUm.isEnabled = false
            }
            2 -> {
                binding.buttonDois.text = texto
                binding.buttonDois.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonDois.isEnabled = false
            }
            3 -> {
                binding.buttonTres.text = texto
                binding.buttonTres.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonTres.isEnabled = false
            }
            4 -> {
                binding.buttonQuatro.text = texto
                binding.buttonQuatro.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonQuatro.isEnabled = false
            }
            5 -> {
                binding.buttonCinco.text = texto
                binding.buttonCinco.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonCinco.isEnabled = false
            }
            6 -> {
                binding.buttonSeis.text = texto
                binding.buttonSeis.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonSeis.isEnabled = false
            }
            7 -> {
                binding.buttonSete.text = texto
                binding.buttonSete.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonSete.isEnabled = false
            }
            8 -> {
                binding.buttonOito.text = texto
                binding.buttonOito.setTextColor(resources.getColor(R.color.yellow, null))
                binding.buttonOito.isEnabled = false
            }
        }
    }

    // Função para verificar se um jogador ganhou
    private fun verificarVitoria(jogador: String): Boolean {
        for (i in 0..2) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) {
                return true
            }
        }
        for (i in 0..2) {
            if (tabuleiro[0][i] == jogador && tabuleiro[1][i] == jogador && tabuleiro[2][i] == jogador) {
                return true
            }
        }
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador) {
            return true
        }
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador) {
            return true
        }
        return false
    }

    // Função para verificar se há empate
    private fun verificarEmpate(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (tabuleiro[i][j].isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    // Função para desativar todos os botões após o término do jogo
    private fun desativarTodosBotoes() {
        binding.buttonZero.isEnabled = false
        binding.buttonUm.isEnabled = false
        binding.buttonDois.isEnabled = false
        binding.buttonTres.isEnabled = false
        binding.buttonQuatro.isEnabled = false
        binding.buttonCinco.isEnabled = false
        binding.buttonSeis.isEnabled = false
        binding.buttonSete.isEnabled = false
        binding.buttonOito.isEnabled = false
    }

    // Função para reiniciar o jogo
    fun reiniciarJogo(view: View) {
        for (i in 0..2) {
            for (j in 0..2) {
                tabuleiro[i][j] = ""
            }
        }

        val buttons = arrayOf(
            binding.buttonZero, binding.buttonUm, binding.buttonDois,
            binding.buttonTres, binding.buttonQuatro, binding.buttonCinco,
            binding.buttonSeis, binding.buttonSete, binding.buttonOito
        )

        for (button in buttons) {
            button.text = ""
            button.setBackgroundColor(resources.getColor(R.color.purple_200, null))
            button.isEnabled = true
        }

        binding.textViewResultado.text = ""
    }
}
