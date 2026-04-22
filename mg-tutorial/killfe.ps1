$port = 4200 

$process = Get-NetTCPConnection | Where-Object { $_.LocalPort -eq $port }

if ($process) {
    Write-Host "Process found with port $port. Killing the process..."
    $processId = $process.OwningProcess
    Stop-Process -Id $processId -Force
    Write-Host "Process with ID $processId has been terminated."
}
else {
    Write-Host "No process found with port $port."
}

