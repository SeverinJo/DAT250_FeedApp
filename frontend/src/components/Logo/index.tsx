import PollIcon from "@mui/icons-material/Poll";
import {Box, Typography} from "@mui/material";

export function Logo(){
  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', color: 'primary.main'}}>
      <PollIcon fontSize={'large'} sx={{ mb: '0.1rem', mr: '0.5rem'}} />
      <Typography variant={"h5"}>Feed App</Typography>
    </Box>
  );
}