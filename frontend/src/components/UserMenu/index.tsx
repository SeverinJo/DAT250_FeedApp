import {Box, Button, ListItemIcon, ListItemText, Menu, MenuItem} from "@mui/material";
import {useState, type SetStateAction} from "react";
import LogoutIcon from '@mui/icons-material/Logout';
import {useCurrentUser} from "../../hooks/useCurrentUser.ts";

export function UserMenu() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const { userInfo } = useCurrentUser();

  const handleClick = (event: { currentTarget: SetStateAction<HTMLElement | null>; }) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };


  return(
    <Box sx={{ display: 'flex' }}>
      <Button variant={'text'} onClick={handleClick}>
        {userInfo?.username || "User"}
      </Button>

      <Menu
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "right",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        slotProps={{
          paper: {
            sx: {
              backgroundColor: 'warning.main',
              borderRadius: '12px'
            }
          }
        }}
      >
        <MenuItem>
          <ListItemIcon>
            <LogoutIcon />
          </ListItemIcon>
          <ListItemText>Logout</ListItemText>
        </MenuItem>
      </Menu>

    </Box>
  );
}