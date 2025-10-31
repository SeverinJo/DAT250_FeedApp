import {Box, Button, Container, Divider, Paper, TextField, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {Logo} from "../../components/Logo";

export function LoginPage() {
  const navigate = useNavigate();
  const [errorState, setErrorState] = useState(false);

  const handleLogin = (e: { preventDefault: () => void; }) => {
    e.preventDefault();
    setErrorState(true);
    // todo
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
        <TextField label={'Username'} error={ errorState } onChange={() => setErrorState(false)}/>
        <TextField label={'Password'} type={'password'} error={ errorState } onChange={() => setErrorState(false)}/>

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
