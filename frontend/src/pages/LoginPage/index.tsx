import {Box, Button, Container, Divider, Paper, TextField, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {Logo} from "../../components/Logo";
import {useLogin} from "../../hooks/useLogin.ts";

export function LoginPage() {
  const navigate = useNavigate();
  const [usernameValue, setUsernameValue] = useState("");
  const [passwordValue, setPasswordValue] = useState("");
  const [errorState, setErrorState] = useState(false);
  const { login } = useLogin();

  const handleLogin = async (e: { preventDefault: () => void; }) => {
    e.preventDefault();
    const success = await login({username: usernameValue, password: passwordValue});
    if(!success) {
        setErrorState(true);
        return;
    }
    navigate("/");
  }

  return (
    <Container sx={{ display: 'flex', flexDirection: 'column', height: '100%', alignItems: 'center'}}>
      <Paper
        elevation={7}
        sx={{
          display: 'flex',
          flexDirection: 'column',
          gap: '1.2rem',
          p: '3rem',
          width: 'fit-content',
          height: 'fit-content',
          mt: '8rem',
          borderRadius: '12px',
          border: '1px solid',
          borderColor: 'primary.main',
          maxWidth: '500'
        }}
        component={'form'}
      >
        <Logo />
        <Divider />
        <TextField
            label={'Username'}
            value={usernameValue}
            error={ errorState }
            onChange={(e) => { setUsernameValue(e.target.value); setErrorState(false) }}
        />
        <TextField
            label={'Password'}
            type={'password'}
            value={passwordValue}
            error={ errorState }
            onChange={(e) => { setPasswordValue(e.target.value); setErrorState(false) }}
        />

        <Button
          type={"submit"}
          variant={'contained'}
          size={'large'}
          onClick={handleLogin}
        >
          Login
        </Button>
      </Paper>

      <Box sx={{ display: 'flex', flexDirection: 'column', mt: '2rem', alignItems: 'center', gap: '0.5rem'}}>
        <Typography variant={'body2'} color={'secondary'}>Not registered yet?</Typography>
        <Button variant={'text'} size={'small'} color={'secondary'} onClick={() => navigate('/register')}>
          Register here
        </Button>
      </Box>
    </Container>
  );
}
