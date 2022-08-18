package com.cursojavafullstack.userdirectory.controllers;

import com.cursojavafullstack.userdirectory.dao.UsuarioDao;
import com.cursojavafullstack.userdirectory.models.Usuario;
import com.cursojavafullstack.userdirectory.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){

        Usuario userLogged = usuarioDao.obtainUserFromCredentials(usuario);

        if (userLogged != null){

            String tokenJwt = jwtUtil.create(String.valueOf(userLogged.getId()), userLogged.getEmail());
            return tokenJwt;
        }

        return "FAIL";
    }


}
