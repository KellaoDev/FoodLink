function atualizarHora() {

    let options = { timeZone: 'America/Sao_Paulo', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    let data = new Date().toLocaleTimeString('pt-BR', options);

    document.getElementById('hora').textContent = data;
}

setInterval(atualizarHora, 1000);