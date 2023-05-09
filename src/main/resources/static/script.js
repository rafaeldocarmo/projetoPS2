var produtos = []

///////// CRUD //////////               /// PPRT SPA QUE É MELHOR ESPERAR ELE FALAR COMO CONECTA COM O REACT, AI A GENTE FAZ POR LA
                                        
async function createAluno() {
  const nome = document.getElementById('new_aluno_nome').value;
  const marca = document.getElementById('new_aluno_tia').value;
  const double = document.getElementById('new_aluno_tia').value;

  const postOptions = {
    method: 'POST',
    body: JSON.stringify({ nome, tia }),
    headers: {
      "Content-Type": "application/json",
    },
  }

  try { 
    const response = await fetch('/api/alunos', postOptions);
    await showAlunos();
  } catch(err) {
    console.error(err);
  }
  
}

async function readAlunos() {
  try {
    const response = await fetch('/api/alunos');
    alunos = await response.json();
  } catch(err) {
    console.error(err);
    return [];
  }
}

async function updateAluno(tia) {
  const aluno = getAlunoByTia(tia);
  const nome = document.getElementById('aluno_nome').value;
  const newTia = document.getElementById('aluno_tia').value;

  const putOptions = {
    method: 'PUT',
    body: JSON.stringify({ nome, tia: newTia }),
    headers: {
      "Content-Type": "application/json",
    },
  }

  try {
    const result = await fetch(`/api/alunos/${aluno.tia}`, putOptions);
    await showAlunos();
  } catch(err) {
    console.error(err);
  }
  
}
///////// FIM DO CRUD //////////


///////// Método úteis /////////
async function showAlunos() {
  await readAlunos();
  const root = document.getElementById("root");
  
  root.innerHTML = "";

  for(let i=0; i<alunos.length; i++) {
    addAlunoTo(root, alunos[i])
  }
}

function addAlunoTo(element, aluno) {
  const newElement = `
  <section>
    <h1>${aluno.nome}</h1>
    <div>TIA: ${aluno.tia}</div>
    <button onclick="loadEditAluno(${aluno.tia})">edit</button>
  </section>
  `;

  element.innerHTML += newElement;
}

function getAlunoByTia(tia) {
  for(let i=0; i<alunos.length; i++) {
    if(alunos[i].tia === tia) return alunos[i];
  }
  return undefined;
}

function loadEditAluno(tia) {
  const aluno = getAlunoByTia(tia);
  
  if(!aluno) {
    console.error(`Problema ao tentar acessar o aluno ${tia}`);
    return;
  }

  document.getElementById('aluno_nome').value = aluno.nome;
  document.getElementById('aluno_tia').value = aluno.tia;
  document.getElementById('edit_button').onclick = () => { updateAluno(aluno.tia)};

}

window.onload = showAlunos;