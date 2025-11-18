import {Navigate, Route, Routes} from "react-router-dom";
import {LoginPage} from "./pages/LoginPage";
import {MainPage} from "./pages/MainPage";
//import {useCurrentUser} from "./hooks/useCurrentUser.ts";
import {useState} from "react";


function App() {

  // simulate login state
  //const [isAuthenticated] = useState(true);
  //const { isAuthenticated } = useCurrentUser();
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem('accessToken')
  );

  return (
    <Routes>
        <Route path="/login" element={<LoginPage onLogin={() => setIsAuthenticated(true)} />} />

      {isAuthenticated ? (
        <>
          <Route path={'/'} element={ <MainPage /> } />
          <Route path={'*'} element={<Navigate to={'/'} />} />
        </>
      ) : (
        <>
          <Route path={'*'} element={<Navigate to={'/login'} />} />
        </>
      )};

    </Routes>
  );
}

export default App
