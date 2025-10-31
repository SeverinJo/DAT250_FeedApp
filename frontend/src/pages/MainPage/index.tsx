import {Logo} from "../../components/Logo";
import {Box, Container, Tab, Tabs} from "@mui/material";
import {UserMenu} from "../../components/UserMenu";
import {type SyntheticEvent, useState} from "react";

import AddchartIcon from '@mui/icons-material/Addchart';
import PollOutlinedIcon from '@mui/icons-material/PollOutlined';
import {a11yProps, CustomTabPanel} from "../../components/CustomTabPanel";
import CreatePollCard from "../../components/CreatePollCard";
import VotePollCard from "../../components/VotePollCard";



export function MainPage(){

  const [tabValue, setTabValue] = useState(0);

  const handleChange = (_event: SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  return(
    <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', height: '100vh' }}>

      <Box component={"header"} sx={{
        display: 'flex',
        alignSelf: 'flex-start',
        width: '100%',
        alignItems: 'center',
        justifyContent: 'space-between',
        my: '1rem'
        }}>
        <Logo/>
        {/*<Typography variant={'body1'} sx={{ }}> username </Typography>*/}
        <UserMenu />
      </Box>

      <Box sx={{ display: 'flex', width: '100%', height: '100%'}}>

        <Tabs value={tabValue} onChange={handleChange} orientation={'vertical'} sx={{ alignSelf: 'center'}}>
          <Tab icon={<PollOutlinedIcon />} label="My Polls" {...a11yProps(0)} />
          <Tab icon={<PollOutlinedIcon />} label="All Polls" {...a11yProps(1)}/>
          <Tab icon={<AddchartIcon />} label="Create Poll" {...a11yProps(2)}/>
        </Tabs>

        {/*TODO: check if list of polls for this user is empty. if empty display "no polls found", if polls list them in readonly mode*/}
        <CustomTabPanel value={tabValue} index={0}>
          <Box sx={{display: 'flex', width: '100%', justifyContent: 'center'}}>
            <VotePollCard></VotePollCard>
          </Box>
        </CustomTabPanel>

        {/*TODO: display list of all polls, paginated*/}
        <CustomTabPanel value={tabValue} index={1}>
          <Box sx={{display: 'flex', width: '100%', justifyContent: 'center'}}>
            <span>Item Two</span>
          </Box>
        </CustomTabPanel>

        <CustomTabPanel value={tabValue} index={2}>
          <Box sx={{display: 'flex', width: '100%', justifyContent: 'center'}}>
            <CreatePollCard></CreatePollCard>
          </Box>
        </CustomTabPanel>
      </Box>


    </Container>
  );
}