// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
  updateUserEmail();
});

function updateUserEmail(){
  document.getElementById('txt-Email-User').outerHTML = localStorage.email;
}


function getHeaders(){

  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
  };
}

async function cargarUsuarios() {

  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();


  let listadoHtml = '';

  for (let usuario of usuarios){
    let buttonDelete = '<a href="#" onclick="deleteUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></iclassName </a>'

    let telefono = usuario.telefono == null ? '-' : usuario.telefono;
    let usuarioHtml = '<tr>\n' +
        '                                            <td>'+usuario.id+'</td>\n' +
        '                                            <td>'+usuario.nombre+' '+usuario.apellido+'</td>\n' +
        '                                            <td>'+usuario.email+'</td>\n' +
        '                                            <td>'+telefono+'</td>\n' +
        '                                            <td>\n' +
        '                                                <a href="#" class="btn btn-info btn-circle btn-sm">\n' +
        '                                                    <i class="fas fa-info-circle"></i>\n' +
        '                                                </a>\n' +
                                                        buttonDelete +
        '                                            </td>\n' +
        '                                        </tr>'

    listadoHtml += usuarioHtml;
  }

  document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}
async function deleteUsuario(id) {

  if (!confirm('¿Desea eliminar este usuario?')){
    return;
  }

  const request = await fetch('api/usuarios/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload();
}