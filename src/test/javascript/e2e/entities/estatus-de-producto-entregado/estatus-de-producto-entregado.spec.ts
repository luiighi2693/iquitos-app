/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    EstatusDeProductoEntregadoComponentsPage,
    EstatusDeProductoEntregadoDeleteDialog,
    EstatusDeProductoEntregadoUpdatePage
} from './estatus-de-producto-entregado.page-object';

const expect = chai.expect;

describe('EstatusDeProductoEntregado e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let estatusDeProductoEntregadoUpdatePage: EstatusDeProductoEntregadoUpdatePage;
    let estatusDeProductoEntregadoComponentsPage: EstatusDeProductoEntregadoComponentsPage;
    let estatusDeProductoEntregadoDeleteDialog: EstatusDeProductoEntregadoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load EstatusDeProductoEntregados', async () => {
        await navBarPage.goToEntity('estatus-de-producto-entregado');
        estatusDeProductoEntregadoComponentsPage = new EstatusDeProductoEntregadoComponentsPage();
        expect(await estatusDeProductoEntregadoComponentsPage.getTitle()).to.eq('Estatus De Producto Entregados');
    });

    it('should load create EstatusDeProductoEntregado page', async () => {
        await estatusDeProductoEntregadoComponentsPage.clickOnCreateButton();
        estatusDeProductoEntregadoUpdatePage = new EstatusDeProductoEntregadoUpdatePage();
        expect(await estatusDeProductoEntregadoUpdatePage.getPageTitle()).to.eq('Create or edit a Estatus De Producto Entregado');
        await estatusDeProductoEntregadoUpdatePage.cancel();
    });

    it('should create and save EstatusDeProductoEntregados', async () => {
        const nbButtonsBeforeCreate = await estatusDeProductoEntregadoComponentsPage.countDeleteButtons();

        await estatusDeProductoEntregadoComponentsPage.clickOnCreateButton();
        await promise.all([estatusDeProductoEntregadoUpdatePage.setNombreInput('nombre')]);
        expect(await estatusDeProductoEntregadoUpdatePage.getNombreInput()).to.eq('nombre');
        await estatusDeProductoEntregadoUpdatePage.save();
        expect(await estatusDeProductoEntregadoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await estatusDeProductoEntregadoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last EstatusDeProductoEntregado', async () => {
        const nbButtonsBeforeDelete = await estatusDeProductoEntregadoComponentsPage.countDeleteButtons();
        await estatusDeProductoEntregadoComponentsPage.clickOnLastDeleteButton();

        estatusDeProductoEntregadoDeleteDialog = new EstatusDeProductoEntregadoDeleteDialog();
        expect(await estatusDeProductoEntregadoDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Estatus De Producto Entregado?'
        );
        await estatusDeProductoEntregadoDeleteDialog.clickOnConfirmButton();

        expect(await estatusDeProductoEntregadoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
