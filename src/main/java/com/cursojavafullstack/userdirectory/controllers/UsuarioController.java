package com.cursojavafullstack.userdirectory.controllers;

import com.cursojavafullstack.userdirectory.dao.UsuarioDao;
import com.cursojavafullstack.userdirectory.models.Usuario;
import com.cursojavafullstack.userdirectory.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean validateToken(String token){

        String userId = jwtUtil.getKey(token);

        return userId != null;
    }

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Matías");
        usuario.setApellido("Moreno");
        usuario.setEmail("matiasmoreno52@gmail.com");
        usuario.setTelefono("1244155661");

        return usuario;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Object> getUsuarios(@RequestHeader(value = "Authorization") String token){

        if (!validateToken(token)){
            return null;
        }

        ArrayList<Object> filteredList = new ArrayList<>();
        Usuario tempUser = new Usuario();
        for (Usuario usuario :usuarioDao.getUsuarios()) {

            tempUser = new Usuario();

            tempUser.setId(usuario.getId());
            tempUser.setNombre(usuario.getNombre());
            tempUser.setApellido(usuario.getApellido());
            tempUser.setEmail(usuario.getEmail());
            tempUser.setTelefono(usuario.getTelefono());

            filteredList.add(tempUser);
        }

        return filteredList;
        //return usuarioDao.getUsuarios();
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registerUsuario(@RequestBody Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());

        usuario.setPassword(hash);

        usuarioDao.register(usuario);
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void deleteUsuario(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){

        if (!validateToken(token)){
            return;
        }

        usuarioDao.delete(id);
    }

    @RequestMapping(value = "usuario124")
    public Usuario editUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Matías");
        usuario.setApellido("Moreno");
        usuario.setEmail("matiasmoreno52@gmail.com");
        usuario.setTelefono("1244155661");

        return usuario;
    }
}
