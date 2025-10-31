import {Navigate, Route, Routes} from "react-router-dom";
import {LoginPage} from "./pages/LoginPage";
import {useState} from "react";
import {MainPage} from "./pages/MainPage";


function App() {

  // simulate login state
  const [isAuthenticated] = useState(true);

  return (
    <Routes>
      {isAuthenticated ? (
        <>
          <Route path={'/'} element={ <MainPage /> } />
          <Route path={'*'} element={<Navigate to={'/'} />} />
        </>
      ) : (
        <>
          <Route path={'/login'} element={<LoginPage/>} />
          <Route path={'*'} element={<Navigate to={'/login'} />} />
        </>
      )};

    </Routes>
  );
}

export default App
