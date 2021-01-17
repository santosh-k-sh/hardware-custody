<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Emaratech Employee's Hardware Custody Report</title>

    <style>
        .styled-table {
            border-collapse: collapse;
            margin: 25px 0;
            font-size: 0.9em;
            font-family: sans-serif;
            min-width: 400px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }

        .styled-table thead tr {
            background-color: #005698;
            color: #ffffff;
            text-align: left;
        }

        .styled-table th,
        .styled-table td {
            padding: 12px 15px;
        }

        .styled-table tbody tr {
            border-bottom: 1px solid #dddddd;
        }

        .styled-table tbody tr:nth-of-type(even) {
            background-color: #f3f3f3;
        }

        .styled-table tbody tr:last-of-type {
            border-bottom: 2px solid #005698;
        }

        .styled-table tbody tr.active-row {
            font-weight: normal;
            color: #005698;
        }
    </style>
</head>
<body>
<h3>Dear ${name},</h3>
<p>${value}</p>

<div>
    <table border="1" cellspacing="0" cellpadding="1" class="styled-table">
        <tr class="tableHeader" style="background-color: #0980e2; font-weight: bold; color: #dddddd">
            <td>Asset Number</td>
            <td>FA Name</td>
            <td>FA Group</td>
            <td>FA Serial Number</td>
            <td>Brand</td>
            <td>Model</td>
        </tr>
        <#foreach hardwareDetail in hardwareDetails>
            <tr class="tableBody active-row">
                <td>${hardwareDetail.assetNumber}</td>
                <td>${hardwareDetail.faName}</td>
                <td>${hardwareDetail.faGroup}</td>
                <td>${hardwareDetail.faSerialNumber}</td>
                <td>${hardwareDetail.brandName}</td>
                <td>${hardwareDetail.model}</td>
            </tr>
        </#foreach>
    </table>
</div>

<p>Regards,</p>
<p>Anees Sheikh</p>

<!--
<div>
    <a href="https://asbnotebook.com">
        <img src='cid:asbnotebook' alt="ASBNotebook" style="width:10%"/>
        <p>ASB Notebook</p>
    </a>
</div>
-->
</body>
</html>
