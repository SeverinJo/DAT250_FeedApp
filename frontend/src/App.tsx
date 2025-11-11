import {Navigate, Route, Routes} from "react-router-dom";
import {LoginPage} from "./pages/LoginPage";
import {MainPage} from "./pages/MainPage";
import {useCurrentUser} from "./hooks/useCurrentUser.ts";


function App() {

  // simulate login state
  //const [isAuthenticated] = useState(true);
  const { isAuthenticated } = useCurrentUser();

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
