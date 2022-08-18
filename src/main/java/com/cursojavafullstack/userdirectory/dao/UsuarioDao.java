package com.cursojavafullstack.userdirectory.dao;

import com.cursojavafullstack.userdirectory.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();

    void delete(Long id);

    void register(Usuario usuario);

    Usuario obtainUserFromCredentials(Usuario usuario);
}
