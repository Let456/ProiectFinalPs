import { Button, TextField } from "@mui/material"
import { loginButtonStyle, parentDivStyle } from "./Login.styles"
import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";


export const Login = (): JSX.Element => {
    const [email, setEmail] = React.useState<string>("");
    const [password, setPassword] = React.useState<string>("");
    const navigate = useNavigate();

    const onChangeEmail = (event: any): void => {
        setEmail(event.target.value)
    }

    const onChangePassword = (event: any): void => {
        setPassword(event.target.value)
    }

    const login = (event: any): void => {
        navigate("/Home")
    }

    return <div style={parentDivStyle}>
        <div style={{ marginTop: 20 }}>
            <TextField id="standard-basic" label="Standard" variant="standard" onChange={onChangeEmail} />
        </div>
        <div style={{ marginTop: 20 }}>
            <TextField id="standard-basic" label="Standard" variant="standard" onChange={onChangePassword} />
        </div>
        <Button style={loginButtonStyle} onClick={login} variant="contained">Login</Button>
    </div>
}