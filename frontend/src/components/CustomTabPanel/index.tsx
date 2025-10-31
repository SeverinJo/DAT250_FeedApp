import type {ReactNode} from "react";
import {Box, type SxProps, type Theme} from "@mui/material";

export interface TabPanelProps {
  children?: ReactNode;
  index: number;
  value: number;
  sx?: SxProps<Theme> | undefined
}

export function CustomTabPanel({ children, value, index, sx, ...other }: TabPanelProps) {
  return (
    <Box
      role='tabpanel'
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      sx={{
        width: '100%',
        ...sx
      }}
      {...other}
    >
      {value === index && <Box sx={{ display: 'flex', p: 3 }}>{children}</Box>}
    </Box>
  );
}

// eslint-disable-next-line react-refresh/only-export-components
export function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}