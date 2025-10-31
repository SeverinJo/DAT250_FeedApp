// import { StrictMode } from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {ThemeProvider} from "@mui/material";
import {theme} from "./styles/theme.ts";
import {BrowserRouter} from "react-router-dom";

createRoot(document.getElementById('root')!).render(
  // <StrictMode>
    <ThemeProvider theme={theme}>
      <BrowserRouter>
          <App />
      </BrowserRouter>
    </ThemeProvider>
  // </StrictMode>,
)
